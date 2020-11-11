<#if table.hasPrimaryKey>
    public ${name} find(${getPrimaryKeysAsParameterString()}) throws SQLException  {
		final String query = """
                SELECT
		<@columnSelection/> 
		FROM ${table.tableName}
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.column.primaryKeyIndex != 0>
			<#if index == 0><#assign index=1><#else>AND </#if>${property.column.columnName} = ?
			</#if>
		</#list>
                """;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ${getPrimaryKeysAsPreparedStatements()}
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) return rowMapper(resultSet);
        } 
        return null;
	}
</#if>