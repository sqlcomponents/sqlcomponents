<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="get${pluralName}Without" parameterClass="map" resultClass="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
		<dynamic prepend="WHERE">		
		<#list properties as property>
			<#if property.column.nullable>
			<isEqual prepend="AND" property="isNull${property.name?cap_first}" compareValue="true">
				${property.columnName} IS NULL 
			</isEqual>	
			</#if>
		</#list>
		</dynamic>
	</select>