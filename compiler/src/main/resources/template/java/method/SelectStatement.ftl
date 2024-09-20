<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.List")>
<#assign a=addImportStatement("java.util.ArrayList")>
<#assign a=addImportStatement("java.util.Optional")>
<#assign a=addImportStatement("java.sql.PreparedStatement")>

public SelectStatementWithWhere select() {
        return new SelectStatementWithWhere(this);
}

public static final class SelectStatementWithWhere extends SelectStatement{

        private SelectStatementWithWhere(final ${name}Store ${name?uncap_first}Store) {
            super(${name?uncap_first}Store,null);
        }

        public SelectStatement where(WhereClause whereClause) throws SQLException {
            return new SelectStatement(super.${name?uncap_first}Store, whereClause);
        }
    }

public static class SelectStatement {

        private final ${name}Store ${name?uncap_first}Store;
        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;


        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final ${name}Store ${name?uncap_first}Store) {
            this(${name?uncap_first}Store,null);
        }

        private SelectStatement(final ${name}Store ${name?uncap_first}Store
                ,final WhereClause whereClause) {
            this.${name?uncap_first}Store = ${name?uncap_first}Store;
            this.whereClause = whereClause;
        }

        public final List<${name}> execute() throws <@throwsblock/> {
            List<${name}> arrays = new ArrayList();
		final String query = <@compress single_line=true>"
                SELECT
		<@columnSelection properties=properties/> 
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                try (java.sql.Connection dbConnection = this.${name?uncap_first}Store.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                
                while (resultSet.next()) {
                                        arrays.add(this.${name?uncap_first}Store.rowMapper(resultSet));
                                }
                                
                } 
                return arrays;
	}

        public final int count() throws SQLException {
                int count = 0;
		final String query = <@compress single_line=true>"
                SELECT
		COUNT(*)
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                try (java.sql.Connection dbConnection = this.${name?uncap_first}Store.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                
                while (resultSet.next()) {
                                        count = resultSet.getInt(1);
                                }
                                
                } 
                return count;
	}
public final SelectQuery sql(final String sql) {
            return new SelectQuery(this, sql);
    }

    public static final class SelectQuery  {

        private final SelectStatement selectStatement;
        private final String sql;
        private final List<Value> values;

        public SelectQuery(final SelectStatement selectStatement, final String sql) {
            this.selectStatement = selectStatement;
            this.sql = sql;
            this.values = new ArrayList<>();
        }


        public SelectQuery param(final Value value) {
            this.values.add(value);
            return this;
        }

        public Optional<${name}> optional() throws <@throwsblock/> {
            ${name} ${name?uncap_first} = null;
            try (java.sql.Connection dbConnection = this.selectStatement.${name?uncap_first}Store.dbDataSource.getConnection(); 
                 PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                int index = 1;
                for (Value value:values
                     ) {
                    value.set(preparedStatement, index++);
                }

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) ${name?uncap_first} = this.selectStatement.${name?uncap_first}Store.rowMapper(resultSet);
            }
            return Optional.ofNullable(${name?uncap_first});
        }

        public List<${name}> list() throws <@throwsblock/>{
            List<${name}> arrays = new ArrayList();
            try (java.sql.Connection dbConnection = this.selectStatement.${name?uncap_first}Store.dbDataSource.getConnection(); 
                 PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                int index = 1;
                for (Value value:values
                     ) {
                    value.set(preparedStatement, index++);
                }

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    arrays.add(this.selectStatement.${name?uncap_first}Store.rowMapper(resultSet));
                }
            }
            return arrays;
        }
    }


        public static final class LimitClause  {
                private final SelectStatement selectStatement;
                private final String asSql;

                private LimitClause(final SelectStatement selectStatement,final int limit) {
                        this.selectStatement = selectStatement;
                        asSql = " LIMIT " + limit;

                        this.selectStatement.limitClause = this;
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
                    return new PageImpl(this.selectStatement.execute(), pageable,
                                selectStatement.count());
                }
                <#else>
                public DataManager.Page<${name}> execute() throws <@throwsblock/> {
                    return DataManager.page(this.selectStatement.execute(), selectStatement.count());
                }
                </#if>

                public static final class OffsetClause  {
                        private final LimitClause limitClause;
                        private final String asSql;

                        private OffsetClause(final LimitClause limitClause,final int offset) {
                                this.limitClause = limitClause;
                                asSql = " OFFSET " + offset;

                                this.limitClause.selectStatement.offsetClause = this;
                        }

                        private String asSql() {
                                return asSql ;
                        }

                        <#if hasJavaClass("org.springframework.data.domain.Page") >
                        public Page<${name}> execute(final Pageable pageable) throws <@throwsblock/> {
                                return this.limitClause.execute(pageable);
                        }
                        <#else>
                        public DataManager.Page<${name}> execute() throws <@throwsblock/> {
                                return this.limitClause.execute();
                        }
                        </#if>


        }

        }
}



    
<#assign a=addImportStatement("java.util.List")>

<#if table.hasPrimaryKey>
<#assign a=addImportStatement("java.util.Optional")>
    public Optional<${name}> select(${getPrimaryKeysAsParameterString()}) throws <@throwsblock/>  {
            return select(${getPrimaryKeysAsParameters()}, null);
    }
    public Optional<${name}> select(${getPrimaryKeysAsParameterString()}, WhereClause whereClause) throws <@throwsblock/>  {
        ${name} ${name?uncap_first} = null;
		final String query = <@compress single_line=true>"
                SELECT
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
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            ${getPrimaryKeysAsPreparedStatements()}
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) ${name?uncap_first} = rowMapper(resultSet);
        }
        return Optional.ofNullable(${name?uncap_first});
	}

    public boolean exists(${getPrimaryKeysAsParameterString()}) throws SQLException {
        final String query = <@compress single_line=true>"
                SELECT
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
        boolean isExists = false;
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            ${getPrimaryKeysAsPreparedStatements()}
            ResultSet resultSet = preparedStatement.executeQuery();

            isExists = resultSet.next();
        }
		return isExists;
	}

</#if>


<#if table.uniqueColumns?? && table.uniqueColumns?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.Optional")>
    <#list table.uniqueColumns as uniqueColumn>
    public Optional<${name}> selectBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws <@throwsblock/> {
        ${name} ${name?uncap_first} = null;
            final String query = <@compress single_line=true>"
                    SELECT
            <@columnSelection properties=properties/>
            FROM ${table.escapedName?j_string}
            WHERE

            ${getUniqueKeysAsWhereClause(uniqueColumn.name)}

                    </@compress>";
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                ${getUniqueKeysAsPreparedStatements(uniqueColumn.name)}
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) ${name?uncap_first} = rowMapper(resultSet);
            }
            return Optional.ofNullable(${name?uncap_first});
    }

    public boolean existsBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws <@throwsblock/> {

            final String query = <@compress single_line=true>"
                    SELECT
            1
            FROM ${table.escapedName?j_string}
            WHERE

            ${getUniqueKeysAsWhereClause(uniqueColumn.name)}

                    </@compress>";
            boolean isExists = false;
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                ${getUniqueKeysAsPreparedStatements(uniqueColumn.name)}
                ResultSet resultSet = preparedStatement.executeQuery();

                isExists = resultSet.next();
            }
            return isExists;
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
    	            <#local pkAsParameterStr = pkAsParameterStr + "${property.name?uncap_first}(${property.name}).set(preparedStatement,${index});\n\t"><#assign index=index+1>
    	        </#list>
    	    </#if>
    	</#list>
    	<#return pkAsParameterStr>
</#function>

