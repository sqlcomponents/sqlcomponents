<#include "base.ftl">

<#function getPrimaryKeysAsPreparedStatements>
	<#local pkAsParameterStr="">
    <#assign index=1>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
        	<#local pkAsParameterStr = pkAsParameterStr + "${property.name?uncap_first}(${property.name}).set(preparedStatement,${index});\n\t"><#assign index=index+1>
        </#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#--  <#function getPreparedValue property insertMap>

<#if insertMap[property.column.columnName]??>
<#return insertMap[property.column.columnName]>
</#if>
	<#if property.entity.table.database.dbType == 'POSTGRES'>
		<#if property.column.typeName == 'xml'>
			<#return "XMLPARSE(document ?)">
		</#if>
    <#elseif insertMap[property.column.columnName]??>
        <#return "X">
    </#if>
	<#return "?">
</#function>  -->

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

<#macro throwsblock>
SQLException 
<#if hasJavaType("com.fasterxml.jackson.databind.JsonNode")>
<#assign a=addImportStatement("com.fasterxml.jackson.core.JsonProcessingException")>,JsonProcessingException
</#if>
<#if hasJavaType("java.net.InetAddress")>
    <#assign a=addImportStatement("java.net.UnknownHostException")>,UnknownHostException
</#if>

</#macro>

<#macro columnSelection><#assign index=0><#list properties as property><#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string}</#list></#macro>

<#macro returningColumnSelection><#assign index=0><#list returningProperties as property><#if index == 0><#assign index=1><#else>,</#if>${property.column.escapedName?j_string}</#list></#macro>
