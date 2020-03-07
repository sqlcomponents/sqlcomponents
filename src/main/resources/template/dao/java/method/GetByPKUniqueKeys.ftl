<#if uniqueConstraintGroupNames?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
	<#list uniqueConstraintGroupNames as uniqueConstraintGroupName>
<#include "signature/GetByPKUniqueKeys.ftl">;
	</#list>
</#if>