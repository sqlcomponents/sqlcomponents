<#if highestPKIndex != 0>
	public Boolean isExisting${name}(${getPrimaryKeysAsParameterString()}) throws SQLException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMap()}
		return false;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>