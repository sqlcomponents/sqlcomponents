<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="get${pluralName}Without" parameterClass="map" resultClass="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
		<where>
		<#list properties as property>
			<#if property.column.nullable>
			<if property="isNull${property.name?cap_first} == true">
				${property.columnName} IS NULL 
			</if>
			</#if>
		</#list>
		</where>
	</select>