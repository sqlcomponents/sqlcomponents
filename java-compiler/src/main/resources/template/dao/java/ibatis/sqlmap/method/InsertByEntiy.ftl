<#if tableType == 'TABLE' >
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<insert id="insert${name}" parameterType="${name}">
		<#if sequenceName?? && highestPKIndex == 1>
			<#list properties as property>
				<#if property.primaryKeyIndex == 1>		
		<selectKey resultType="${property.dataType}" keyProperty="${property.name}" >
		SELECT ${sequenceName}.NEXTVAL AS ID FROM DUAL 
		</selectKey>
				</#if>
			</#list>		 
		</#if>
		INSERT INTO ${tableName} (
		<#assign index=0>
		<#list properties as property>		
			<#if index == 0><#assign index=1><#else>,</#if>"${property.columnName}"
		</#list>
		)	     
	    VALUES (
	    <#assign index=0>
	    <#list properties as property>		
			<#if index == 0><#assign index=1><#else>,</#if>${getInsertValue(property.columnName,property.name,property.sqlDataType,orm)}
		</#list>
	    )	
	</insert>
</#if>	