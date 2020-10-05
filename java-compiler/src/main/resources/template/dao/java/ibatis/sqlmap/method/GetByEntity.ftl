<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="get${name}ByEntity" parameterType="${name}" resultType="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
	    <@dynamicWhere prefix=""/>
	</select>
<#if orm.pagination >
	<select id="get${name}ByEntityPage1" parameterType="map" resultType="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
	    <@dynamicPaginatedWhere prefix="${name?uncap_first}." pageStart="1"/>
	</select>
	<select id="get${name}ByEntityPage" parameterType="map" resultType="${name}">
	SELECT * FROM (
		SELECT
		<@columnSelection/>
		,row_number() over  (order by ${getPrimaryKeysAsCommaSeparated()}) ROW_NUMBER
		FROM ${tableName} 
	    <@dynamicWhere prefix="${name?uncap_first}."/>
	) WHERE ROW_NUMBER BETWEEN #pageNumber${getDataBaseSpecificDefault("NUMBER",orm)}# AND #pageSize${getDataBaseSpecificDefault("NUMBER",orm)}# ORDER BY ROW_NUMBER
	</select>	
</#if>
