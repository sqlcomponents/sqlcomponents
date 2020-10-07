<#if highestPKIndex != 0>
<#include "/template/dao/java/method/signature/IsExisting.ftl">{
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMap()}
		return false;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>