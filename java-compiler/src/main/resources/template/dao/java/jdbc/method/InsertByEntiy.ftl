<#if tableType == 'TABLE' >
	public final int create(final ${name} ${name?uncap_first}) throws SQLException  {
		final String query = """
		<#if sequenceName?? && highestPKIndex == 1>
			<#list properties as property>
				<#if property.primaryKeyIndex == 1>		
		<selectKey resultType="${property.dataType}" keyProperty="${property.name}" >
		SELECT ${sequenceName}.NEXTVAL AS ID FROM DUAL 
		</selectKey>
				</#if>
			</#list>		 
		</#if>
		INSERT INTO ${tableName} (
		<#assign index=0>
		<#list properties as property>		
			<#if property.column.generatedColumn == false >
			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}"
			</#if>
		</#list>
		)	     
	    VALUES (
	    <#assign index=0>
	    <#list properties as property>		
			<#if index == 0><#assign index=1><#else>,</#if>?
		</#list>
	    )	
		""";

		try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query))
        {
			return preparedStatement.executeUpdate();

        }
				
	}
</#if>