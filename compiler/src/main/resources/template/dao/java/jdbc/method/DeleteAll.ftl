<#if table.tableType == 'TABLE' >
    public int deleteAll() throws SQLException  {
    	final String query = "DELETE FROM ${table.tableName}";
        try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return preparedStatement.executeUpdate();
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.PreparedStatement")>
</#if>