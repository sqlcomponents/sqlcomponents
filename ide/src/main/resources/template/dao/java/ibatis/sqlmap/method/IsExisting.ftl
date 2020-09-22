<#if highestPKIndex != 0>
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="isExisting${name}" parameterClass="map" resultClass="Boolean">
		SELECT
		1
		FROM ${tableName} 
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex != 0>		
			<#if index == 0><#assign index=1><#else>,</#if>${property.columnName} = #${property.name}#
			</#if>
		</#list>	
	</select>
</#if>