	@SuppressWarnings("unchecked")
<#include "/template/dao/java/method/signature/Without.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getNullablePropsAsParameterStringNoTypeMap()}
		return (List<${name}>) getSqlMapClientTemplate().queryForList("get${pluralName}Without",map) ;
	}<#assign a=addImportStatement("java.util.HashMap")>
