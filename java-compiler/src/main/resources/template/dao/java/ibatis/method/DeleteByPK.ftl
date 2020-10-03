<#if tableType == 'TABLE' >
<#include "/template/dao/java/method/signature/DeleteByPK.ftl"> {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMap()}
		return getSqlMapClientTemplate().delete("delete${name}ByPk",map) ;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>