<#if tableType == 'TABLE' >
	<delete id="delete${name}ByPk" parameterClass="map">
		DELETE FROM ${tableName} 
		WHERE
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex != 0>		
			<#if index == 0><#assign index=1><#else>,</#if>${property.columnName} = #${property.name}#
			</#if>
		</#list>	
	</delete>
</#if>