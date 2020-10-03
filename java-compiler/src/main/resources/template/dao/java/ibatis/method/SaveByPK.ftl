<#if tableType == 'TABLE' >
	<#include "/template/dao/java/method/signature/SaveByPK.ftl"> {
		return getSqlMapClientTemplate().update("save${name}ByPk",${name?uncap_first}) ;
	}
</#if>