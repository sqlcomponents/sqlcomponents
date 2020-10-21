<#if tableType == 'TABLE' >
public int delete(${name} search${name}) throws SQLException  {
		

		StringBuffer dynamicWhere = new StringBuffer();
		<@dynamicWhere prefix="search${name}"/>
		
        try (Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement()) {
			final String query = "DELETE FROM ${tableName}" + (dynamicWhere.isEmpty() ? "" : (" WHERE" + dynamicWhere.substring(4)));
			System.out.println(query);
            return 0;
        }
	}<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.sql.Statement")>
</#if>