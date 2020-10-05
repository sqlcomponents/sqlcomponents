<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/DeleteByEntity.ftl"> {
		return sqlSession.delete("delete${name}ByEntity",search${name}) ;
	}
</#if>