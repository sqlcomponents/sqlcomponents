
    public List<${name}> get${pluralName}Without(${getNullablePropsAsParameterString()}) throws SQLException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getNullablePropsAsParameterStringNoTypeMap()}
		return null;
	}<#assign a=addImportStatement("java.util.HashMap")>
