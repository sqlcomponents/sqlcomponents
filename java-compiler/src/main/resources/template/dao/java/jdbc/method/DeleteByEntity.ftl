<#if tableType == 'TABLE' >
public int delete(${name} search${name}) throws SQLException  {
		final String query = """	
		DELETE FROM ${tableName} 
                """;

		StringBuffer dynamicWhere = new StringBuffer();
		<@dynamicWhere prefix="search${name}"/>
		
        try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			${getPrimaryKeysAsPreparedStatements()}
            return preparedStatement.executeUpdate();
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)>
</#if>