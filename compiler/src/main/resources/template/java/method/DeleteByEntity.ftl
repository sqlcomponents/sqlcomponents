<#if table.tableType == 'TABLE' >
    public int delete(WhereClause whereClause) throws SQLException  {
        String whereSQL = whereClause.asSql() ;
    	final String query = "DELETE FROM ${table.escapedName?j_string}" + (whereSQL == null ? "" : (" WHERE " + whereSQL));
        try (java.sql.Connection dbConnection = dataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.Statement")>
</#if>