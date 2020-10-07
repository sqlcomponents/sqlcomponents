<#if highestPKIndex != 0>
<#include "/template/dao/java/method/signature/GetByPK.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMap()}
				return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>