<#if highestPKIndex != 0>
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="getAll${pluralName}" parameterClass="map" resultClass="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
	</select>
</#if>