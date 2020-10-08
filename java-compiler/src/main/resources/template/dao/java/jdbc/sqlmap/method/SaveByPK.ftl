<#if tableType == 'TABLE' >
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<update id="save${name}ByPk" parameterType="${name}">
	MERGE INTO ${tableName} TARGET_TABLE
    USING DUAL
	    ON (
			<#assign index=0>
			<#list properties as property>
				<#if property.primaryKeyIndex != 0>		
				<#if index == 0><#assign index=1><#else>AND </#if>"${property.columnName}" = ${getUpdateValue(property.columnName,property.name,property.sqlDataType,driverName)}
				</#if>
			</#list>
		)
		<#if properties?size != highestPKIndex>
	  WHEN MATCHED THEN
	    UPDATE SET 
	    <#assign index=0>
		<#list properties as property>
			<#if property.primaryKeyIndex == 0>		
			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}" = ${getUpdateValue(property.columnName,property.name,property.sqlDataType,driverName)}
			</#if>
		</#list>
		</#if>
	  WHEN NOT MATCHED THEN
	    INSERT (
		    <#assign index=0>
			<#list properties as property>		
				<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}"
			</#list>
		)
	    VALUES (
		    <#assign index=0>
		    <#list properties as property>		
				<#if index == 0><#assign index=1><#else>,</#if>${getInsertValue(property.columnName,property.name,property.sqlDataType,driverName)}
			</#list>
		)
	</update>
</#if>