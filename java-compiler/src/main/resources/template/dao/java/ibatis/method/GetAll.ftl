<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.util.List")>

<#include "/template/dao/java/method/signature/GetAll.ftl"> {
		return sqlSession.selectList("getAll${pluralName}") ;
	}
