<#--

A convenience method, in any language which has a concept of methods, is just that. A method that makes things more convenient.

This usually means taking something that is complex or verbose, and making it more accessible. Examples include pretty much everything in helper classes like Collections or Arrays. As well as factory methods (to a certain extent, there are reasons for factories beyond simple convenience).

For a more formal definition from Wikipedia (http://en.wikipedia.org/wiki/Convenience_function):

A convenience function is a non-essential subroutine in a programming library or framework which is intended to ease commonly performed tasks.

We use similar to JPA syntax for all the convinient methods.

-->

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


public int delete(final DataSource dataSource,${getPrimaryKeysAsParameterString()}) throws SQLException  {
		final String query = <@compress single_line=true>"DELETE FROM ${table.escapedName?j_string}
					WHERE
					<#assign index=0>
					<#list properties as property>
						<#if property.column.primaryKeyIndex != 0>
						<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ?
						</#if>
					</#list></@compress>";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
        ${getPrimaryKeysAsPreparedStatements()}
        return sqlBuilder.execute(dataSource);
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