<#if table.hasPrimaryKey>
    public ${name} find(${getPrimaryKeysAsParameterString()}) throws SQLException  {
        ${name} ${name?uncap_first} = null;
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

            if (resultSet.next()) ${name?uncap_first} = rowMapper(resultSet);
        } 
        return ${name?uncap_first};
	}
</#if>