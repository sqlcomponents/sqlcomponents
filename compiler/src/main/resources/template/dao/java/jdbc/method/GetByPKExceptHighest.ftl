<#if table.hasPrimaryKey>

    public List<${name}> get${pluralName}(${getPrimaryKeysAsParameterStringExceptHighest()}) throws SQLException  {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getPrimaryKeysAsParameterStringNoTypeMapExceptHighest()}
				return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
</#if>
