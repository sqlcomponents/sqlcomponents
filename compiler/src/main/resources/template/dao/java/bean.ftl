<#include "base.ftl">
<#if beanPackage?? && beanPackage?length != 0 >package ${beanPackage};</#if>

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
	<#if !property.nullable >
    <#assign a=addImportStatement("javax.validation.constraints.NotNull")>
    @NotNull
    </#if>
	<#if property.size != 0 && property.decimalDigits == 0 >
	<#assign a=addImportStatement("javax.validation.constraints.Size")>
	@Size(max=${property.size?c})
	<#elseif property.size != 0>
	<#assign a=addImportStatement("javax.validation.constraints.Digits")>
	@Digits(integer=${property.size?c},fraction=${property.decimalDigits?c})
	</#if>
	<#if orm.validationMap?? && orm.validationMap[property.columnName]?? >
	<#assign a=addImportStatement("javax.validation.constraints.Pattern")>
	@Pattern(regexp="${orm.validationMap[property.columnName]}")
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
