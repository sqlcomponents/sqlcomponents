<#assign importStatementsList = []>

<#assign methodSignatureList = []>

<#function getClassName str> 
	<#return str?split(".")?last>
</#function>

<#function getJDBCClassName str> 
	<#local pkAsParameterStr="${getClassName(str)}">
	<#if pkAsParameterStr == "Integer">
			<#local pkAsParameterStr="Int">
	</#if>
	<#return pkAsParameterStr>
</#function>

<#function wrapGet wText property > 
<#switch property.dataType>
  <#case "java.util.Date">
	 <#return "${wText}.get${property.name?cap_first}() == null ? null : new java.sql.Date(${wText}.get${property.name?cap_first}().getTime())">
  <#default>
  <#return "${wText}.get${property.name?cap_first}()">
</#switch>

	
</#function>

<#function getProperty propertyName> 
	<#list properties as property>
		<#if property.name == propertyName>
			<#return property> 
		</#if>
	</#list>
	<#return null> 
</#function>

<#function getNullablePropsAsParameterString>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.nullable>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>
			<#local pkAsParameterStr = pkAsParameterStr +"Boolean isNull" +property.name?cap_first >			
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getPrimaryKeysAsParameterString>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.primaryKeyIndex != 0>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>

			<#local pkAsParameterStr = pkAsParameterStr + getClassName(property.dataType) + " " +property.name >
			<#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>


<#function getPrimaryKeysAsParameterStringExceptHighest>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.primaryKeyIndex != 0>
			<#if property.primaryKeyIndex != table.highestPKIndex>
				<#if index == 0>
					<#local index=1>
				<#else>
					<#local pkAsParameterStr = pkAsParameterStr + ",">
				</#if>
	
				<#local pkAsParameterStr = pkAsParameterStr + getClassName(property.dataType) + " " +property.name >
				<#local a=addImportStatement(property.dataType)>
			</#if>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>


<#function addMethodSignature methodSignature>
<#if methodSignature?contains(".") 
		&& !methodSignature?contains("java.lang")
		&& !methodSignaturesList?seq_contains(methodSignature)>
<#assign methodSignaturesList = methodSignaturesList + [methodSignature]>
</#if>
<#return methodSignature>
</#function>

<#function addImportStatement importStatement>
<#if importStatement?contains(".") 
		&& !importStatement?contains("java.lang.String")
		&& !importStatement?contains("java.lang.Integer")
		&& !importStatement?contains("java.lang.Long")
		&& !importStatement?contains("java.lang.Float")
		&& !importStatement?contains("java.lang.Double")
		&& !importStatement?contains("java.lang.Short")
		&& !importStatement?contains("java.lang.Byte")
		&& !importStatementsList?seq_contains(importStatement)>
<#assign importStatementsList = importStatementsList + [importStatement]>
</#if>
<#return "">
</#function>

<#macro importStatements>
<#list importStatementsList?sort as importStatement>
import ${importStatement};
</#list>
</#macro>

<#function getUniqueKeysAsParameterString uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>		
		<#if property.uniqueConstraintGroup?? && property.uniqueConstraintGroup == uniqueConstraintGroupName>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>
			<#local pkAsParameterStr = pkAsParameterStr + getClassName(property.dataType) + " " +property.name >
			<#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>
