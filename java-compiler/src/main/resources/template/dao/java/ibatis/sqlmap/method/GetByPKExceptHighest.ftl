<#if highestPKIndex &gt; 1>
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="get${name}ByPkExceptHighest" parameterType="map" resultType="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex != 0 && property.primaryKeyIndex != highestPKIndex >		
			<#if index == 0><#assign index=1><#else>,</#if>${property.columnName} = #${property.name}#
			</#if>
		</#list>
	</select>
</#if>