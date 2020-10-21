<#if tableType == 'TABLE' >
	public int deleteById(${getPrimaryKeysAsParameterString()}) throws SQLException  {
		final String query = """
                DELETE FROM ${tableName} 
					WHERE
					<#assign index=0>
					<#list properties as property>
						<#if property.primaryKeyIndex != 0>		
						<#if index == 0><#assign index=1><#else>,</#if>${property.columnName} = ?
						</#if>
					</#list>
                """;
        try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			${getPrimaryKeysAsPreparedStatements()}
            return preparedStatement.executeUpdate();
        }
	}<#assign a=addImportStatement("java.sql.PreparedStatement")>
</#if>