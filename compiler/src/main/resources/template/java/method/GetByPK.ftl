<#if table.hasPrimaryKey>
<#assign a=addImportStatement("java.util.Optional")>
    public Optional<${name}> find(${getPrimaryKeysAsParameterString()}) throws SQLException  {
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