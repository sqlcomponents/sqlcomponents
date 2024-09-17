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

<#macro throwsblock>
SQLException 
<#if hasJavaType("com.fasterxml.jackson.databind.JsonNode")>
<#assign a=addImportStatement("com.fasterxml.jackson.core.JsonProcessingException")>,JsonProcessingException
</#if>
<#if hasJavaType("java.net.InetAddress")>
    <#assign a=addImportStatement("java.net.UnknownHostException")>,UnknownHostException
</#if>

</#macro>