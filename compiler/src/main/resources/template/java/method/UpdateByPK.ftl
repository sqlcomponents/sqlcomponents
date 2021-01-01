<#if table.tableType == 'TABLE' >
    public int update(${name} ${name?uncap_first}) throws SQLException {
        final String query = <@compress single_line=true>"
		UPDATE ${table.escapedName?j_string} SET
        		<#assign index=0>
        		<#list properties as property>
        			<#if property.column.primaryKeyIndex == 0>
        			<#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string} = ?
        			</#if>
        		</#list>
        		WHERE
        	    <#assign index=0>
        		<#list properties as property>
        			<#if property.column.primaryKeyIndex != 0>
        			<#if index == 0><#assign index=1><#else> AND </#if>${property.column.escapedName?j_string} = ?
        			</#if>
        		</#list>
		</@compress>";

		try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {

        <#assign index=0>
        <#assign column_index=1>
        <#list properties as property>
            <#if property.column.primaryKeyIndex == 0>
            <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first+".get"+property.name?cap_first + "()",property)});
                                                          				<#assign column_index = column_index + 1>
            </#if>
        </#list>

        <#assign index=0>
        <#list properties as property>
            <#if property.column.primaryKeyIndex != 0>
            <#if index == 0><#assign index=1><#else></#if>preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapSet(name?uncap_first+".get"+property.name?cap_first + "()",property)});
                                                          				<#assign column_index = column_index + 1>
            </#if>
        </#list>

			return preparedStatement.executeUpdate();
        }
	}
</#if>