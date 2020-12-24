<#if table.tableType == 'TABLE' >
    public int deleteAll() throws SQLException  {
        int deletedRows = 0 ;
    	final String query = "DELETE FROM ${table.escapedName?j_string}";
        try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            deletedRows = preparedStatement.executeUpdate();
        }
        return deletedRows;
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.PreparedStatement")>
</#if>