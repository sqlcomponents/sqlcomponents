<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.List")>
<#assign a=addImportStatement("java.util.ArrayList")>
<#assign a=addImportStatement("java.util.Optional")>
<#assign a=addImportStatement("java.sql.PreparedStatement")>

public SelectStatementWithWhere select() {
        return new SelectStatementWithWhere();
}

public final class SelectStatementWithWhere extends SelectStatement{

        private SelectStatementWithWhere() {
            super(null);
        }

        public SelectStatement where(WhereClause whereClause) throws SQLException {
            return new SelectStatement(whereClause);
        }
    }

public sealed class SelectStatement permits SelectStatementWithWhere {


        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;


        public LimitClause limit(final int limit) {
                return new LimitClause(limit);
        }

        private SelectStatement() {
            this(null);
        }

        private SelectStatement(final WhereClause whereClause) {
            this.whereClause = whereClause;
        }

        public final List<${name}> execute(final DataSource dataSource) throws <@throwsblock/> {
            
		final String query = <@compress single_line=true>"SELECT
		<@columnSelection properties=properties/> 
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                return dataManager.sql(query).queryForList(${name}Store.this::rowMapper).execute(dataSource);
	}

        public final int count(final DataSource dataSource) throws SQLException {
		final String query = <@compress single_line=true>"SELECT
		COUNT(<#if primaryKeyProperties?size == 0
                >*<#else
                    ><@columnSelection properties=primaryKeyProperties
                /></#if>)
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                return dataManager.sql(query).queryForInt().execute(dataSource);
	}
public final SelectQuery sql(final String sql) {
            return new SelectQuery(sql);
    }

    public final class SelectQuery  {

        private final String sql;
        private final List<Value<?,?>> values;

        public SelectQuery(final String sql) {
            this.sql = sql;
            this.values = new ArrayList<>();
        }


        public SelectQuery param(final Value<?,?> value) {
            this.values.add(value);
            return this;
        }

        public Optional<${name}> optional(final DataSource dataSource) throws <@throwsblock/> {
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (Value<?,?> value:values) {
                value.set(sqlBuilder);
            }
            
            return Optional.ofNullable(sqlBuilder.queryForOne(${name}Store.this::rowMapper).execute(dataSource));
        }

        public List<${name}> list(final DataSource dataSource) throws <@throwsblock/>{
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(sql);

            for (Value<?,?> value:values) {
                value.set(sqlBuilder);
            }
            
            return sqlBuilder.queryForList(${name}Store.this::rowMapper).execute(dataSource);
        }
    }


        public final class LimitClause  {

                private final String asSql;

                private LimitClause(final int limit) {
                        asSql = " LIMIT " + limit;

                        limitClause = this;
                }

                private String asSql() {
                        return asSql ;
                }

                public OffsetClause offset(final int offset) {
                        return new OffsetClause(this,offset);
                }

                <#if hasJavaClass("org.springframework.data.domain.Page") >
                <#assign a=addImportStatement("org.springframework.data.domain.Page")>
                <#assign a=addImportStatement("org.springframework.data.domain.PageImpl")>
                <#assign a=addImportStatement("org.springframework.data.domain.Pageable")>
                public Page<${name}> execute(final Pageable pageable) throws <@throwsblock/> {
                    return new PageImpl(this.selectStatement.execute(dataSource), pageable,
                                selectStatement.count());
                }
                <#else>
                public DataManager.Page<${name}> execute(final DataSource dataSource) throws <@throwsblock/> {
                    return DataManager.page(SelectStatement.this.execute(dataSource), count(dataSource));
                }
                </#if>

                public final class OffsetClause  {
                        private final LimitClause limitClause;
                        private final String asSql;

                        private OffsetClause(final LimitClause limitClause,final int offset) {
                                this.limitClause = limitClause;
                                asSql = " OFFSET " + offset;

                                offsetClause = this;
                        }

                        private String asSql() {
                                return asSql ;
                        }

                        <#if hasJavaClass("org.springframework.data.domain.Page") >
                        public Page<${name}> execute(final Pageable pageable) throws <@throwsblock/> {
                                return this.limitClause.execute(pageable);
                        }
                        <#else>
                        public DataManager.Page<${name}> execute(final DataSource dataSource) throws <@throwsblock/> {
                                return this.limitClause.execute(dataSource);
                        }
                        </#if>


        }

        }
}



    
<#assign a=addImportStatement("java.util.List")>

<#if table.hasPrimaryKey>
<#assign a=addImportStatement("java.util.Optional")>
    public Optional<${name}> select(final DataSource dataSource,${getPrimaryKeysAsParameterString()}) throws <@throwsblock/>  {
            return select(dataSource,${getPrimaryKeysAsParameters()}, null);
    }
    public Optional<${name}> select(final DataSource dataSource,${getPrimaryKeysAsParameterString()}, WhereClause whereClause) throws <@throwsblock/>  {
        
		final String query = <@compress single_line=true>"SELECT
		<@columnSelection properties=properties/>
		FROM ${table.escapedName?j_string}
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.column.primaryKeyIndex != 0>
			<#if index == 0><#assign index=1><#else>AND </#if>${property.column.escapedName?j_string} = ?
			</#if>
		</#list>
                </@compress>"

                + ( whereClause == null ? "" : (" AND " + whereClause.asSql()) );

        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        ${getPrimaryKeysAsPreparedStatements()}

        return Optional.ofNullable(sqlBuilder.queryForOne(this::rowMapper).execute(dataSource));
            
    }
        
    public boolean exists(final DataSource dataSource,${getPrimaryKeysAsParameterString()}) throws SQLException {
        final String query = <@compress single_line=true>"SELECT
		1
		FROM ${table.escapedName?j_string}
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.column.primaryKeyIndex != 0>
			<#if index == 0><#assign index=1><#else>AND </#if>${property.column.escapedName?j_string} = ?
			</#if>
		</#list>
                </@compress>";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

        ${getPrimaryKeysAsPreparedStatements()}

		return sqlBuilder.queryForExists().execute(dataSource);
	}

</#if>


<#if table.uniqueColumns?? && table.uniqueColumns?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.Optional")>
    <#list table.uniqueColumns as uniqueColumn>
    public Optional<${name}> selectBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(final DataSource dataSource,${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws <@throwsblock/> {
        
            final String query = <@compress single_line=true>"SELECT
            <@columnSelection properties=properties/>
            FROM ${table.escapedName?j_string}
            WHERE

            ${getUniqueKeysAsWhereClause(uniqueColumn.name)}

                    </@compress>";

        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        ${getUniqueKeysAsPreparedStatements(uniqueColumn.name)}

        return Optional.ofNullable(sqlBuilder.queryForOne(this::rowMapper).execute(dataSource));

            
    }

    public boolean existsBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(final DataSource dataSource,${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws <@throwsblock/> {

            final String query = <@compress single_line=true>"SELECT
            1
            FROM ${table.escapedName?j_string}
            WHERE

            ${getUniqueKeysAsWhereClause(uniqueColumn.name)}

                    </@compress>";
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        ${getUniqueKeysAsPreparedStatements(uniqueColumn.name)}

        return sqlBuilder.queryForExists().execute(dataSource);
    }

    </#list>
</#if>


<#function getUniqueKeysAsParameterString uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
    <#local index=0>
	<#list table.uniqueColumns as uniqueColumn>
	    <#if uniqueColumn.name == uniqueConstraintGroupName>
	        <#list uniqueColumn.columns as column>
	            <#local property=getPropertyByColumnName(column.columnName)>
	            <#if index == 0>
                    <#local index=1>
                <#else>
                    <#local pkAsParameterStr = pkAsParameterStr + "," >
                </#if>
                <#local pkAsParameterStr = pkAsParameterStr + getClassName(property.dataType) + " " +property.name >
                <#local a=addImportStatement(property.dataType)>
	        </#list>
	    </#if>
	</#list>
	<#return pkAsParameterStr>
</#function>

<#function getUniqueKeysAsMethodSignature uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
    <#local index=0>
	<#list table.uniqueColumns as uniqueColumn>
	    <#if uniqueColumn.name == uniqueConstraintGroupName>
	        <#list uniqueColumn.columns as column>
	            <#local property=getPropertyByColumnName(column.columnName)>
	            <#if index == 0>
                    <#local index=1>
                <#else>
                    <#local pkAsParameterStr = pkAsParameterStr + "And" >
                </#if>
                <#local pkAsParameterStr = pkAsParameterStr  + property.name?cap_first >
	        </#list>
	    </#if>
	</#list>
	<#return pkAsParameterStr>
</#function>

<#function getUniqueKeysAsWhereClause uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
    <#local index=0>
	<#list table.uniqueColumns as uniqueColumn>
	    <#if uniqueColumn.name == uniqueConstraintGroupName>
	        <#list uniqueColumn.columns as column>

	            <#if index == 0>
                    <#local index=1>
                <#else>
                    <#local pkAsParameterStr = pkAsParameterStr + " AND" >
                </#if>
                <#local pkAsParameterStr = pkAsParameterStr  + column.columnName + "= ?" >
	        </#list>
	    </#if>
	</#list>
	<#return pkAsParameterStr>
</#function>


<#function getUniqueKeysAsPreparedStatements uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
        <#local index=1>
    	<#list table.uniqueColumns as uniqueColumn>
    	    <#if uniqueColumn.name == uniqueConstraintGroupName>
    	        <#list uniqueColumn.columns as column>
    	            <#local property=getPropertyByColumnName(column.columnName)>
    	            <#local pkAsParameterStr = pkAsParameterStr + "${property.name?uncap_first}(${property.name}).set(sqlBuilder);\n\t"><#assign index=index+1>
    	        </#list>
    	    </#if>
    	</#list>
    	<#return pkAsParameterStr>
</#function>

