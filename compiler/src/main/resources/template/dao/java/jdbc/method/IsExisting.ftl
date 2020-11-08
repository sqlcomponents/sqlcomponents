<#if table.hasPrimaryKey>
	public boolean exists(${getPrimaryKeysAsParameterString()}) throws SQLException {
        final String query = """
                SELECT
		1
		FROM ${table.tableName}
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex != 0>
			<#if index == 0><#assign index=1><#else>AND </#if>${property.columnName} = ?
			</#if>
		</#list>
                """;
        boolean isExists = false;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ${getPrimaryKeysAsPreparedStatements()}
            ResultSet resultSet = preparedStatement.executeQuery();

            isExists = resultSet.next();
        }
		return isExists;
	}
</#if>