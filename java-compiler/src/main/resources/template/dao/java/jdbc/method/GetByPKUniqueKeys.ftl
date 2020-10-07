<#if uniqueConstraintGroupNames?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
	<#list uniqueConstraintGroupNames as uniqueConstraintGroupName>
<#include "/template/dao/java/method/signature/GetByPKUniqueKeys.ftl">  {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getUniqueKeysAsParameterStringNoTypeMap(uniqueConstraintGroupName)}
				return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
	</#list>
</#if>