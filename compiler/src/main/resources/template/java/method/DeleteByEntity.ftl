<#if table.tableType == 'TABLE' >
    public int delete(WhereClause whereClause) throws SQLException  {
        String whereSQL = whereClause.asSql() ;
    	final String query = "DELETE FROM ${table.tableName}" + (whereSQL == null ? "" : (" WHERE " + whereSQL));
        try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.Statement")>
</#if>