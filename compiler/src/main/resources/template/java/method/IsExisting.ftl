<#if table.hasPrimaryKey>
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
        try (java.sql.Connection dbConnection = dataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            ${getPrimaryKeysAsPreparedStatements()}
            ResultSet resultSet = preparedStatement.executeQuery();

            isExists = resultSet.next();
        }
		return isExists;
	}
</#if>