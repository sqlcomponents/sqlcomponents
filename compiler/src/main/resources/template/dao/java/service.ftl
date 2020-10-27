<#include "base.ftl">
<#if daoPackage?? && daoPackage?length != 0 >package ${daoPackage};</#if>

import java.sql.SQLException;

<#assign capturedOutput>
/**
 *   
 *
 */ 
public interface ${serviceName}Dao {

	<#list methods as method>
		public void ${method.name}(${getParameterString(method)}) throws SQLException;		
	</#list>

}
</#assign>
<@importStatements/>

${capturedOutput}

<#function getParameterString method>
	<#local pkAsParameterStr="">
	<#local index=0>
	<#list method.inputParameters as property>
			<#if index == 0>
				<#local index=1>
			<#else>
				<#local pkAsParameterStr = pkAsParameterStr + "," >
			</#if>

			<#local pkAsParameterStr = pkAsParameterStr + getClassName(property.dataType) + " " +property.name >
			<#local a=addImportStatement(property.dataType)>
	</#list>
	<#return pkAsParameterStr> 
</#function>