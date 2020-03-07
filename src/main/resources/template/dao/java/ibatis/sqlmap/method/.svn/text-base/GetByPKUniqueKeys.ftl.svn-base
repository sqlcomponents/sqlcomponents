<#if uniqueConstraintGroupNames?size != 0 >
	<#list uniqueConstraintGroupNames as uniqueConstraintGroupName>
	<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="get${name}By${uniqueConstraintGroupName}" parameterClass="map" resultClass="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.uniqueConstraintGroup?? && property.uniqueConstraintGroup == uniqueConstraintGroupName>
			<#if index == 0><#assign index=1><#else> AND </#if>${property.columnName} = #${property.name}#
			</#if>
		</#list>	
	</select>
	</#list>
</#if>