<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.util.List")>

public List<${name}> findAll() throws SQLException {
		final String query = """
                SELECT
		<@columnSelection/> 
		FROM ${tableName} 
                """;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
			List<${name}> arrays = new ArrayList();
            while (resultSet.next()) {
				arrays.add(rowMapper(resultSet));
			}
			return arrays;
        } 
	}
<#assign a=addImportStatement("java.util.List")>
<#assign a=addImportStatement("java.util.ArrayList")>