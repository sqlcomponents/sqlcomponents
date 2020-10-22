<#if tableType == 'TABLE' >
    public int update${name}(${name} ${name?uncap_first},${name} search${name}) throws SQLException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("${name?uncap_first}",${name?uncap_first});
		map.put("search${name}",search${name});
		return 0;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>