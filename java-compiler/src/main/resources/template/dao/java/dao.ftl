<#include "base.ftl">
<#if daoPackage?? && daoPackage?length != 0 >package ${daoPackage};</#if>

import java.sql.SQLException;

<#assign capturedOutput>
/**
 *   
 *
 */ 
public interface ${name}Dao${orm.daoSuffix} {

	<#list orm.methodSpecification as method>
		<#include "method/${method}.ftl">				
	</#list>

	<#--
	<#if exportedKeys?size != 0>
	public List<${name}> get${name}s(Search${name} search${name}) throws SQLException;
		<#assign a=addImportStatement(javaPackageName+ ".search.Search" + name)>
	</#if>	
	-->	
}
</#assign>
<@importStatements/>

${capturedOutput}