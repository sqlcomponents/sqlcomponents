<#assign importStatementsList = []>

<#function getClassName str> 
	<#return str?split(".")?last>
</#function>

<#function getPreparedValue property> 
	
	<#if property.entity.table.database.dbType == 'POSTGRES'>
		<#if property.column.typeName == 'xml'>
			<#return "XMLPARSE(document ?)">
		</#if>
	</#if>		
	<#return "?">
</#function>

<#function getJDBCClassName str> 
	<#local pkAsParameterStr="${getClassName(str)}">
	<#if pkAsParameterStr == "Integer">
			<#local pkAsParameterStr="Int">
	<#elseif pkAsParameterStr == "Character">
	    <#local pkAsParameterStr="String">
	<#elseif pkAsParameterStr == "LocalDate">
    	<#local pkAsParameterStr="Date">
    <#elseif pkAsParameterStr == "LocalTime">
        <#local pkAsParameterStr="Time">
	<#elseif pkAsParameterStr == "LocalDateTime">
		<#local pkAsParameterStr="Timestamp">
	<#elseif pkAsParameterStr == "ByteBuffer">
		<#local pkAsParameterStr="Bytes">
	<#elseif pkAsParameterStr == "JSONObject">
	<#local pkAsParameterStr="Object">
	<#elseif pkAsParameterStr == "UUID">
	<#local pkAsParameterStr="Object">
	<#elseif pkAsParameterStr == "Duration">
	<#local pkAsParameterStr="Object">
	</#if>
	<#return pkAsParameterStr>
</#function>

<#function wrapSet wText property >
<#switch property.dataType>
  <#case "java.time.LocalDate">
	 <#return "${wText} == null ? null : java.sql.Date.valueOf(${wText})">
  <#case "java.time.LocalTime">
  	 <#return "${wText} == null ? null : java.sql.Time.valueOf(${wText})">
  <#case "java.time.LocalDateTime">
  	 <#return "${wText} == null ? null : java.sql.Timestamp.valueOf(${wText})">
  <#case "java.lang.Character">
  	 <#return "${wText} == null ? null : String.valueOf(${wText})">
<#case "java.nio.ByteBuffer">
  	 <#return "${wText} == null ? null : ${wText}.array()">
  <#case "org.json.JSONObject">
  	 <#return "this.convert${property.column.typeName?cap_first}.apply(${wText})">
    <#case "java.util.UUID">
  	 <#return "this.convert${property.column.typeName?cap_first}.apply(${wText})">
	<#case "java.time.Duration">
	<#return "this.convert${property.column.typeName?cap_first}.apply(${wText})">
  <#default>
  <#return "${wText}">
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

			<#local pkAsParameterStr = pkAsParameterStr + nameOfObject + ".get"+ property.name?cap_first + "()" >
            <#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr>
</#function>

<#function getGeneratedPrimaryKeysFromRS>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list generatedPrimaryKeyProperties as property>

			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>

			<#local pkAsParameterStr = pkAsParameterStr + "res.get"+ getJDBCClassName(property.dataType) + "(" +index + ")" >
            <#local a=addImportStatement(property.dataType)>

	</#list>
	<#return pkAsParameterStr>
</#function>

<#function getPrimaryKeysFromRS>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>

			<#local pkAsParameterStr = pkAsParameterStr + "res.get"+ getJDBCClassName(property.dataType) + "(\"" +property.column.columnName + "\")" >
            <#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr>
</#function>


<#function getPrimaryKeysAsParameterStringExceptHighest>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.primaryKeyIndex != 0>
			<#if property.column.primaryKeyIndex != table.highestPKIndex>
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

<#function getUniqueKeysAsParameterString uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
    <#local index=0>
	<#list table.uniqueColumns as uniqueColumn>
	    <#if uniqueColumn.name == uniqueConstraintGroupName>
	        <#list uniqueColumn.columns as column>
	            <#local property=getPropertyByColumnName(column.columnName)>
	            <#if index == 0>
                    <#local index=1>
                <#else>
                    <#local pkAsParameterStr = pkAsParameterStr + "," >
                </#if>
                <#local pkAsParameterStr = pkAsParameterStr + getClassName(property.dataType) + " " +property.name >
                <#local a=addImportStatement(property.dataType)>
	        </#list>
	    </#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getUniqueKeysAsMethodSignature uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
    <#local index=0>
	<#list table.uniqueColumns as uniqueColumn>
	    <#if uniqueColumn.name == uniqueConstraintGroupName>
	        <#list uniqueColumn.columns as column>
	            <#local property=getPropertyByColumnName(column.columnName)>
	            <#if index == 0>
                    <#local index=1>
                <#else>
                    <#local pkAsParameterStr = pkAsParameterStr + "And" >
                </#if>
                <#local pkAsParameterStr = pkAsParameterStr  + property.name?cap_first >
	        </#list>
	    </#if>
	</#list>
	<#return pkAsParameterStr>
</#function>
