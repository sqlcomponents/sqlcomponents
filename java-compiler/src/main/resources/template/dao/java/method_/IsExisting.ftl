<#if highestPKIndex != 0>
<#assign a=addImportStatement(beanPackage+"."+name)>
<#include "signature/IsExisting.ftl">;
</#if>