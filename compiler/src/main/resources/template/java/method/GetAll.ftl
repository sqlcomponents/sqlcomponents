<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.util.List")>
    public List<${name}> select() throws SQLException {
		final String query = <@compress single_line=true>"
                SELECT
		<@columnSelection/> 
		FROM ${table.escapedName?j_string}
                </@compress>";
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