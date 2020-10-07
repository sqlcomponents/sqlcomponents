<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<select id="get${pluralName}Without" parameterType="map" resultType="${name}">
		SELECT
		<@columnSelection/> 
		FROM ${tableName} 
		<where>
		<#list properties as property>
			<#if property.column.nullable>
			<if test="isNull${property.name?cap_first} == true">
				${property.columnName} IS NULL 
			</if>
			</#if>
		</#list>
		</where>
	</select>