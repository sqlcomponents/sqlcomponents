<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/UpdateByEntiy.ftl">{
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("${name?uncap_first}",${name?uncap_first});
		map.put("search${name}",search${name});		
		return sqlSession.update("update${name}ByEntity",map) ;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>