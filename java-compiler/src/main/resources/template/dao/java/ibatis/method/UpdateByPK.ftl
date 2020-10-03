<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/UpdateByPK.ftl">{
		return getSqlMapClientTemplate().update("update${name}ByPk",${name?uncap_first}) ;
	}
</#if>