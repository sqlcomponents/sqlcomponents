<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/UpdateByPK.ftl">{
		return sqlSession.update("update${name}ByPk",${name?uncap_first}) ;
	}
</#if>