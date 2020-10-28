<#if tableType == 'TABLE' >
	public final int create(final ${name} ${name?uncap_first}) throws SQLException  {
		final String query = """
		INSERT INTO ${tableName} (
		<#assign index=0>
		<#list properties as property>		
			<#if property.column.generatedColumn == "NO" >
			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}"
			</#if>
		</#list>
		)	     
	    VALUES (
	    <#assign index=0>
	    <#list properties as property>		
			<#if index == 0><#if sequenceName?? && highestPKIndex == 1>
			<#list properties as property><#if property.primaryKeyIndex == 1>nextval('${sequenceName}')</#if></#list><#else>    ?</#if><#assign index=1><#else>            ,?</#if>
		</#list>
	    )	
		""";

		try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {
			<#assign index=0>
			<#assign column_index=1>
			<#list properties as property>
			<#if index == 0>
				<#if sequenceName?? && property.primaryKeyIndex == 1>
				<#else>
				preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapGet(name?uncap_first,property)});
				<#assign column_index = column_index + 1>
				</#if>
			<#assign index=1>
			<#else>
			preparedStatement.set${getJDBCClassName(property.dataType)}(${column_index},${wrapGet(name?uncap_first,property)});
			<#assign column_index = column_index + 1>
			</#if>
			</#list>	
			return preparedStatement.executeUpdate();
        }
				
	}
</#if>