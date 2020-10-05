<#if tableType == 'TABLE' >
	<#include "/template/dao/java/method/signature/SaveByPK.ftl"> {
		return sqlSession.update("save${name}ByPk",${name?uncap_first}) ;
	}
</#if>