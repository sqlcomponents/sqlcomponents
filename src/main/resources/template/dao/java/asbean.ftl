<#include "base.ftl">
package ${beanPackage};

<#assign capturedOutput>
<#if remarks?exists>
/**
 * ${remarks}  
 *
 */ 
</#if>
public class ${name} {

<#list properties as property>
	<#if property.remarks?exists>
	/**
	 * ${property.remarks}  
	 */ 
	</#if>
	private ${getClassName(property.dataType)} ${property.name};	
	<#assign a=addImportStatement(property.dataType)>
	
</#list>
<#list properties as property>
	public ${getClassName(property.dataType)} get${property.name?cap_first}() {
		return ${property.name};
	}
	
	public void set${property.name?cap_first}(${getClassName(property.dataType)} ${property.name}) {
		this.${property.name} = ${property.name};
	}

</#list>
}
</#assign>
<@importStatements/>
${capturedOutput}
