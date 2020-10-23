<#if tableType == 'TABLE' >
    public int update(${getPrimaryKeysAsParameterString()},${name} ${name?uncap_first}) throws SQLException {
final String query = """
		UPDATE ${tableName} SET
        		<#assign index=0>
        		<#list properties as property>
        			<#if property.primaryKeyIndex == 0>
        			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}" = ?
        			</#if>
        		</#list>
        		WHERE
        	    <#assign index=0>
        		<#list properties as property>
        			<#if property.primaryKeyIndex != 0>
        			<#if index == 0><#assign index=1><#else> AND </#if>"${property.columnName}" = ?
        			</#if>
        		</#list>
		""";

		try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {

        <#assign index=0>
        <#assign column_index=1>
        <#list properties as property>
            <#if property.primaryKeyIndex == 0>
            <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapGet(name?uncap_first,property)});
                                                          				<#assign column_index = column_index + 1>
            </#if>
        </#list>

        <#assign index=0>
        <#list properties as property>
            <#if property.primaryKeyIndex != 0>
            <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${property.name});
                                                          				<#assign column_index = column_index + 1>
            </#if>
        </#list>

			return preparedStatement.executeUpdate();
        }
	}
</#if>