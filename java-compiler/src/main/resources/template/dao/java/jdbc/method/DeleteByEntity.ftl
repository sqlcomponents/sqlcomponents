<#if tableType == 'TABLE' >
    public int delete(Criteria criteria) throws SQLException  {
        String whereClause = criteria.asSql() ;
    	final String query = "DELETE FROM ${tableName}" + (whereClause == null ? "" : (" WHERE " + whereClause));
    	System.out.println(query);
        try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.Statement")>
</#if>