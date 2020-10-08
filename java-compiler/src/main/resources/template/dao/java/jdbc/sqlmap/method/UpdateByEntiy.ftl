<#if tableType == 'TABLE' >
	<update id="update${name}ByEntity" parameterType="map">
		UPDATE ${tableName} SET 
		<#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex == 0>		
			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}" = ${getUpdateValue(property.columnName,name?uncap_first +"."+ property.name,property.sqlDataType,driverName)}
			</#if>
		</#list>		
		<@dynamicWhere prefix="search${name}."/>
	</update>
</#if>