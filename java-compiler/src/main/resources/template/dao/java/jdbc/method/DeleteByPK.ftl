<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/DeleteByPK.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMap()}
		return 0;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>