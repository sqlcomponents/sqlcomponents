<#include "/template/dao/java/base.ftl">
<#assign aliasStatementsList = []>

<#function addAliasStatement aliasName aliasType>
	<#local aliasStatement="<typeAlias alias=\""+aliasName+"\" type=\""+aliasType+"\"/>">
	<#if !aliasStatementsList?seq_contains(aliasStatement)>
		<#assign aliasStatementsList = aliasStatementsList + [aliasStatement]>
	</#if>
	<#return "">
</#function>

<#function getUpdateValue columnName propertyName propertySqlDataType orm>
<#local updateValue="#"+ propertyName + getDataBaseSpecificDefault(propertySqlDataType,orm) +"#">
	<#if orm.defaults??>
		<#list orm.defaults as default>
			<#if default.onUpdate && default.columnName == columnName>
				<#local updateValue=default.defaultValue>
				<#break>
			</#if>
		</#list>
	</#if>
<#return updateValue>
</#function>

<#function getDataBaseSpecificDefault propertySqlDataType orm>
<#local dataBaseSpecificInsert="">
<#if orm.crawlerConfig.driverName == "oracle.jdbc.OracleDriver">
<#switch propertySqlDataType>
  <#case "VARCHAR2">
	 <#local dataBaseSpecificInsert=":VARCHAR:NOENTRY">
   <#break>
   <#case "VARCHAR">
	 <#local dataBaseSpecificInsert=":VARCHAR:NOENTRY">
   <#break>
   <#case "BLOB">
	 <#local dataBaseSpecificInsert=":BLOB">
   <#break>
   <#case "DATE">
	 <#local dataBaseSpecificInsert=":DATE">
   <#break>
    <#case "TIMESTAMP">
	 <#local dataBaseSpecificInsert=":TIMESTAMP">
   <#break>
   <#case "NUMBER">
	 <#local dataBaseSpecificInsert=":NUMERIC:-9999">
   <#break>
  <#case "medium">
</#switch>
</#if> 
<#return dataBaseSpecificInsert>
</#function>

<#function getInsertValue columnName propertyName propertySqlDataType orm>
<#local insertValue="#"+ propertyName + getDataBaseSpecificDefault(propertySqlDataType,orm) +"#">
	<#if orm.defaults?? >
	<#list orm.defaults as default>
		<#if default.onInsert && default.columnName == columnName>
			<#local insertValue=default.defaultValue>
			<#break>
		</#if>
	</#list>
	</#if>
<#return insertValue>
</#function>

<#function getPrimaryKeysAsParameterStringNoTypeMap>
	<#local pkAsParameterStr="">

	<#list properties as property>
		<#if property.primaryKeyIndex != 0>
			<#local pkAsParameterStr = pkAsParameterStr + "map.put(\"" + property.name+ "\"," + property.name+");\n\t">
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>


<#function getNullablePropsAsParameterStringNoTypeMap>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.column.nullable>
				<#if index == 0>
					<#local index=1>
				<#else>
					<#local pkAsParameterStr = pkAsParameterStr + "\n\t\t" >
				</#if>
				<#local pkAsParameterStr = pkAsParameterStr + "map.put(\"isNull" + property.name?cap_first+ "\",isNull" + property.name?cap_first+");">
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getPrimaryKeysAsParameterStringNoTypeMapExceptHighest>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.primaryKeyIndex != 0>
			<#if property.primaryKeyIndex != highestPKIndex>
				<#if index == 0>
					<#local index=1>
				<#else>
					<#local pkAsParameterStr = pkAsParameterStr + "," >
				</#if>
				<#local pkAsParameterStr = "map.put(\"" + property.name+ "\"," + property.name+");">
			</#if>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getUniqueKeysAsParameterStringNoTypeMap uniqueConstraintGroupName>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>		
		<#if property.uniqueConstraintGroup?? && property.uniqueConstraintGroup == uniqueConstraintGroupName>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "\n\t\t" >
			</#if>
			<#local pkAsParameterStr = pkAsParameterStr+ "map.put(\"" + property.name+ "\"," + property.name+");">
			<#local a=addImportStatement(property.dataType)>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>

<#function getPrimaryKeysAsCommaSeparated>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list properties as property>
		<#if property.primaryKeyIndex != 0>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>
			<#local pkAsParameterStr = pkAsParameterStr + property.columnName>
		</#if>
	</#list>
	<#return pkAsParameterStr> 
</#function>


<#macro columnSelection>
	<#assign index=0>
	<#list properties as property>			
		<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}" AS "${property.name}"			
	</#list>
</#macro>




<#macro dynamicWhere prefix>
		<where>
		<#list properties as property>
			<#if property.sqlDataType?index_of("VARCHAR") !=  -1>
			<if test="${prefix}${property.name} != null">
				AND ${property.columnName} like #${prefix}${property.name}${getDataBaseSpecificDefault(property.sqlDataType,orm)}#
			</if>
			<#elseif property.sqlDataType?index_of("BLOB") ==  -1>
			<if test="${prefix}${property.name} != null">
				AND ${property.columnName} like #${prefix}${property.name}${getDataBaseSpecificDefault(property.sqlDataType,orm)}#
			</if>
			</#if>
		</#list>
		</where>
</#macro>

<#macro dynamicPaginatedWhere prefix pageStart>
		<where>
		<#list properties as property>
			<#if property.sqlDataType?index_of("VARCHAR") !=  -1>
			<isNotEmpty  property="${prefix}${property.name}">
				${property.columnName} like #${prefix}${property.name}${getDataBaseSpecificDefault(property.sqlDataType,orm)}#
			</isNotEmpty>	
			<#elseif property.sqlDataType?index_of("BLOB") ==  -1>
			<if  test="${prefix}${property.name} != null">
				${property.columnName} like #${prefix}${property.name}${getDataBaseSpecificDefault(property.sqlDataType,orm)}#
			</if>
			</#if>						
		</#list>
			<#if pageStart == "1">
			<isNotNull  property="pageSize">
				ROWNUM &lt;= #pageSize${getDataBaseSpecificDefault("NUMBER",orm)}#
			</if>
			</#if>
		</where>
</#macro>

<#macro aliasStatements>

	<#list aliasStatementsList?sort as aliasStatement>
	${aliasStatement}
	</#list>
	
</#macro>