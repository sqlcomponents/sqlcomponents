<#assign a=addImportStatement(beanPackage+"."+name)><#assign a=addImportStatement("java.util.List")>
	public List<${name}> get${pluralName}(${name} search${name}) throws SQLException 