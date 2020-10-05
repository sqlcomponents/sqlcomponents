<#if highestPKIndex &gt; 1>
	@SuppressWarnings("unchecked")
<#include "/template/dao/java/method/signature/GetByPKExceptHighest.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMapExceptHighest()}
		return getSqlMapClientTemplate().selectList("get${name}ByPkExceptHighest",map) ;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>
