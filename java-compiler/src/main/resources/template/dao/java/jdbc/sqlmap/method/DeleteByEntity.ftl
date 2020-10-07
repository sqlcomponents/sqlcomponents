<#if tableType == 'TABLE' >
<#assign a=addAliasStatement(name,beanPackage+"."+name)>
	<delete id="delete${name}ByEntity" parameterType="${name}">
		DELETE FROM ${tableName} 
		<@dynamicWhere prefix=""/>	    	
	</delete>
</#if>