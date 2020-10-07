	@SuppressWarnings("unchecked")
<#include "/template/dao/java/method/signature/Without.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getNullablePropsAsParameterStringNoTypeMap()}
		return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
