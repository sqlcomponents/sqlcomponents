<#if table.tableType == 'TABLE' >
    public int deleteAll() throws SQLException  {
    	final String query = "DELETE FROM ${table.tableName}";
        try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.Statement")>
</#if>