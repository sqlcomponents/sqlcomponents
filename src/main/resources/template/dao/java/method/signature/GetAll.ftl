<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.util.List")>
	public List<${name}> getAll${pluralName}() throws SQLException	