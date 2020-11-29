<#include "/template/java/base.ftl">

<#function getPrimaryKeysAsPreparedStatements>
	<#local pkAsParameterStr="">
    <#assign index=1>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
        	<#local pkAsParameterStr = pkAsParameterStr + "preparedStatement.set${getJDBCClassName(property.dataType)}(${index}," + property.name+");\n\t"><#assign index=index+1>
        </#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>


<#function getPrimaryKeysAsCommaSeparated>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>
			<#local pkAsParameterStr = pkAsParameterStr + property.column.columnName>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>


<#macro columnSelection><#assign index=0><#list properties as property><#if index == 0><#assign index=1><#else>,</#if>\"${property.column.columnName}\"</#list></#macro>
