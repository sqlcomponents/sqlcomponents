	@SuppressWarnings("unchecked")
<#include "/template/dao/java/method/signature/Without.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getNullablePropsAsParameterStringNoTypeMap()}
		return sqlSession.selectList("get${pluralName}Without",map) ;
	}<#assign a=addImportStatement("java.util.HashMap")>
