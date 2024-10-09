<#include "base.ftl">

<#function getPrimaryKeysAsPreparedStatements>
	<#local pkAsParameterStr="">
    <#assign index=1>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
        	<#local pkAsParameterStr = pkAsParameterStr + "sqlBuilder.param(${property.name?uncap_first}(${property.name}));\n\t"><#assign index=index+1>
        </#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#macro throwsblock>
SQLException 
</#macro>