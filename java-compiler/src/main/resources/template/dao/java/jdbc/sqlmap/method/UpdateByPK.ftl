<#if tableType == 'TABLE' >
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<update id="update${name}ByPk" parameterType="${name}">
		UPDATE ${tableName} SET 
		<#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex == 0>		
			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}" = ${getUpdateValue(property.columnName,property.name,property.sqlDataType,orm)}
			</#if>
		</#list>
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex != 0>		
			<#if index == 0><#assign index=1><#else> AND </#if>"${property.columnName}" = ${getUpdateValue(property.columnName,property.name,property.sqlDataType,orm)}
			</#if>
		</#list>	
	</update>
</#if>