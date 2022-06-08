<#if table.uniqueConstraintGroupNames?? && table.uniqueConstraintGroupNames?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
	<#list table.uniqueConstraintGroupNames as uniqueConstraintGroupName>
    public ${name} get${name}By${uniqueConstraintGroupName}(${getUniqueKeysAsParameterString(uniqueConstraintGroupName)}) throws SQLException   {
		HashMap<String,Object> map = new HashMap<String,Object>();
		${getUniqueKeysAsParameterStringNoTypeMap(uniqueConstraintGroupName)}
				return null;
	}
	</#list>
</#if>


<#if table.uniqueColumns?? && table.uniqueColumns?size != 0 >
<#assign a=addImportStatement(beanPackage+"."+name)>
    <#list table.uniqueColumns as uniqueColumn>
    public ${name} get${name}By${getUniqueKeysAsMethodSignature(uniqueColumn.name)}(${getUniqueKeysAsParameterString(uniqueColumn.name)}) {
        return null;
    }
    </#list>
</#if>