<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.List")>
<#assign a=addImportStatement("java.util.ArrayList")>

public SelectStatement select() {
        return new SelectStatement(this);
}
public SelectStatement select(WhereClause whereClause) throws SQLException  {
        return new SelectStatement(this,whereClause);
}

public static final class SelectStatement {

        private final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix};
        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;

        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}) {
            this(${name?uncap_first}Store${orm.daoSuffix},null);
        }

        private SelectStatement(final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}
                ,final WhereClause whereClause) {
            this.${name?uncap_first}Store${orm.daoSuffix} = ${name?uncap_first}Store${orm.daoSuffix};
            this.whereClause = whereClause;
        }

        public List<${name}> execute() throws SQLException {
		final String query = <@compress single_line=true>"
                SELECT
		<@columnSelection/> 
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                try (java.sql.Connection dbConnection = this.${name?uncap_first}Store${orm.daoSuffix}.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<${name}> arrays = new ArrayList();
                while (resultSet.next()) {
                                        arrays.add(this.${name?uncap_first}Store${orm.daoSuffix}.rowMapper(resultSet));
                                }
                                return arrays;
                } 
	}

        public int count() throws SQLException {
                int count = 0;
		final String query = <@compress single_line=true>"
                SELECT
		COUNT(*)
		FROM ${table.escapedName?j_string}
                </@compress>" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                try (java.sql.Connection dbConnection = this.${name?uncap_first}Store${orm.daoSuffix}.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<${name}> arrays = new ArrayList();
                while (resultSet.next()) {
                                        count = resultSet.getInt(1);
                                }
                                return count;
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

                public List<${name}> execute() throws SQLException {
                        return this.selectStatement.execute();
                }

                public OffsetClause offset(final int offset) {
                        return new OffsetClause(this,offset);
                }

                public ${orm.application.name}Manager.Page<${name}> page() throws SQLException {
                    return ${orm.application.name}Manager.page(execute(), selectStatement.count());
                }

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

                        public List<${name}> execute() throws SQLException {
                                return this.limitClause.execute();
                        }

                        public ${orm.application.name}Manager.Page<${name}> page() throws SQLException {
                                return this.limitClause.page();
                        }

        }

        }
}



    
<#assign a=addImportStatement("java.util.List")>

<#if table.hasPrimaryKey>
<#assign a=addImportStatement("java.util.Optional")>
    public Optional<${name}> select(${getPrimaryKeysAsParameterString()}) throws SQLException  {
        ${name} ${name?uncap_first} = null;
		final String query = <@compress single_line=true>"
                SELECT
		<@columnSelection/>
		FROM ${table.escapedName?j_string}
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.column.primaryKeyIndex != 0>
			<#if index == 0><#assign index=1><#else>AND </#if>${property.column.escapedName?j_string} = ?
			</#if>
		</#list>
                </@compress>";
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
    public Optional<${name}> selectBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws SQLException {
        ${name} ${name?uncap_first} = null;
            final String query = <@compress single_line=true>"
                    SELECT
            <@columnSelection/>
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

    public boolean existsBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws SQLException {

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
    	            <#local pkAsParameterStr = pkAsParameterStr + "preparedStatement.set${getJDBCClassName(property.dataType)}(${index},${wrapSet(property.name,property)});\n\t"><#assign index=index+1>
    	        </#list>
    	    </#if>
    	</#list>
    	<#return pkAsParameterStr>
</#function>

