<#assign importStatementsList = []>

<#function getClassName str> 
	<#return str?split(".")?last>
</#function>

<#function getColumnType str>
	<#local columnTypePrefix="java.sql.Types.">
	<#if str == "JSON">
	    <#local columnType = columnTypePrefix + "VARCHAR">
	<#else>
		<#local columnType = columnTypePrefix + str>
	</#if>
	<#return columnType>
</#function>

<#-- Reads OUT using JDBC getters compatible with registerOutParameter(sqlTypes from metadata). -->
<#function callableOutScalarExpression indexStr dataTypeFqn>
	<#local n = getClassName(dataTypeFqn)>
	<#switch n>
		<#case "Byte"><#return "((byte) callableStatement.getInt("+indexStr+"))">
		<#case "Short"><#return "((short) callableStatement.getInt("+indexStr+"))">
		<#case "Integer"><#return "callableStatement.getInt("+indexStr+")">
		<#case "Long"><#return "callableStatement.getLong("+indexStr+")">
		<#case "Float"><#return "callableStatement.getFloat("+indexStr+")">
		<#case "Double"><#return "callableStatement.getDouble("+indexStr+")">
		<#case "Boolean"><#return "callableStatement.getBoolean("+indexStr+")">
		<#case "String"><#return "callableStatement.getString("+indexStr+")">
		<#case "BigDecimal"><#return "callableStatement.getBigDecimal("+indexStr+")">
		<#default><#return "callableStatement.getObject("+indexStr+", "+dataTypeFqn+".class)">
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

<#function getPropertyByColumnName columnName>
	<#list properties as property>
		<#if property.column.columnName == columnName>
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

<#function getPrimaryKeysAsParameters>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>

			<#local pkAsParameterStr = pkAsParameterStr + property.name >
			<#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr>
</#function>

<#function getPrimaryKeysAsSetters>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#local pkAsParameterStr = pkAsParameterStr + "this." + property.name + " = "+ property.name + ";\n" >
			<#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getPrimaryKeysAsObjectInstances>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#local pkAsParameterStr = pkAsParameterStr + "private final " +  getClassName(property.dataType) + " " +property.name + ";\n" >
			<#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getPrimaryKeysAsParameterString>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
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

<#function getPrimaryKeysFromModel nameOfObject>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>

			<#local pkAsParameterStr = pkAsParameterStr + nameOfObject + "."+ property.name + "()" >
            <#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr>
</#function>


<#function addImportStatement importStatement>
<#if importStatement?contains(".") 
		&& !importStatement?contains("java.lang.")
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

