<#include "/template/dao/java/base.ftl">
<#assign aliasStatementsList = []>

<#function addAliasStatement aliasName aliasType>
	<#local aliasStatement="<typeAlias alias=\""+aliasName+"\" type=\""+aliasType+"\"/>">
	<#if !aliasStatementsList?seq_contains(aliasStatement)>
		<#assign aliasStatementsList = aliasStatementsList + [aliasStatement]>
	</#if>
	<#return "">
</#function>

<#function getUpdateValue columnName propertyName propertySqlDataType driverName>
<#local updateValue="#{"+ propertyName + getDataBaseSpecificDefault(propertySqlDataType,driverName) +"}">
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

<#function getDataBaseSpecificDefault propertySqlDataType driverName>
<#local dataBaseSpecificInsert="">
<#if driverName == "Oracle JDBC driver">
<#switch propertySqlDataType>
  <#case "VARCHAR2">
	 <#local dataBaseSpecificInsert=",jdbcType=VARCHAR">
   <#break>
   <#case "VARCHAR">
	 <#local dataBaseSpecificInsert=",jdbcType=VARCHAR">
   <#break>
   <#case "BLOB">
	 <#local dataBaseSpecificInsert=",jdbcType=BLOB">
   <#break>
   <#case "DATE">
	 <#local dataBaseSpecificInsert=",jdbcType=DATE">
   <#break>
    <#case "TIMESTAMP">
	 <#local dataBaseSpecificInsert=",jdbcType=TIMESTAMP">
   <#break>
   <#case "NUMBER">
	 <#local dataBaseSpecificInsert=",jdbcType=NUMERIC">
   <#break>
  <#case "medium">
</#switch>
</#if> 
<#return dataBaseSpecificInsert>
</#function>

<#function getInsertValue columnName propertyName propertySqlDataType driverName>
<#local insertValue="#"+ propertyName + getDataBaseSpecificDefault(propertySqlDataType,driverName) +"#">
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



<#function getPrimaryKeysAsPreparedStatements>
	<#local pkAsParameterStr="">

	<#list properties as property>
		<#if property.primaryKeyIndex != 0>
			
		</#if>
	</#list>
	<#return pkAsParameterStr> 
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
		<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}" 			
	</#list>
</#macro>




<#macro dynamicWhere prefix>

		<#list properties as property>
			<#if property.sqlDataType?index_of("VARCHAR") !=  -1>
			if(${prefix}.get${property.name?cap_first}() != null) {
				dynamicWhere.append("AND ${property.columnName} like '").append(${prefix}.get${property.name?cap_first}()).append("' ");
			}
			<#elseif property.sqlDataType?index_of("BLOB") ==  -1>
			if(${prefix}.get${property.name?cap_first}() != null) {
				dynamicWhere.append("AND ${property.columnName} like '").append(${prefix}.get${property.name?cap_first}()).append("' ");
			}
			</#if>
		</#list>

</#macro>

<#macro dynamicPaginatedWhere prefix pageStart>
		<trim prefix="WHERE" prefixOverrides="AND |OR ">
		<#list properties as property>
			<#if property.sqlDataType?index_of("VARCHAR") !=  -1>
			<if  test="${prefix}${property.name} != null">
				AND ${property.columnName} like #${prefix}${property.name}${getDataBaseSpecificDefault(property.sqlDataType,driverName)}#
			</if>
			<#elseif property.sqlDataType?index_of("BLOB") ==  -1>
			<if  test="${prefix}${property.name} != null">
				AND ${property.columnName} like #${prefix}${property.name}${getDataBaseSpecificDefault(property.sqlDataType,driverName)}#
			</if>
			</#if>						
		</#list>
			<#if pageStart == "1">
			<if test="pageSize$ != null">
				ROWNUM &lt;= #pageSize${getDataBaseSpecificDefault("NUMBER",driverName)}#
			</if>
			</#if>
		</trim>
</#macro>

<#macro aliasStatements>

	<#list aliasStatementsList?sort as aliasStatement>
	${aliasStatement}
	</#list>
	
</#macro>