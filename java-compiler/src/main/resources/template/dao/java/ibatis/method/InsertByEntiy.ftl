<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/InsertByEntiy.ftl"> {
		return sqlSession.insert("insert${name}", ${name?uncap_first});
	}
</#if>