<#if table.uniqueColumns?? && table.uniqueColumns?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
<#assign a=addImportStatement("java.util.Optional")>
    <#list table.uniqueColumns as uniqueColumn>
    public Optional<${name}> findBy${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(${getUniqueKeysAsParameterString(uniqueColumn.name)}) throws SQLException {
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

