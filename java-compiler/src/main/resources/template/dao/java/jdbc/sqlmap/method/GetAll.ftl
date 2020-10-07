<#if highestPKIndex != 0>
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="getAll${pluralName}" parameterType="map" resultType="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
	</select>
</#if>