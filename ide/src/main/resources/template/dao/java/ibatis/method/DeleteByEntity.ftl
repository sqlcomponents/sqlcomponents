<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/DeleteByEntity.ftl"> {
		return getSqlMapClientTemplate().delete("delete${name}ByEntity",search${name}) ;
	}
</#if>