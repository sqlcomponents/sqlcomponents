<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.util.List")>
	@SuppressWarnings("unchecked")
<#include "/template/dao/java/method/signature/GetAll.ftl"> {
		return (List<${name}>) getSqlMapClientTemplate().queryForList("getAll${pluralName}") ;
	}
