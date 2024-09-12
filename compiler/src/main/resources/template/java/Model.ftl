<#include "base.ftl">
<#if beanPackage?? && beanPackage?length != 0 >package ${beanPackage};</#if>

<#assign capturedOutput>
<#if table.remarks?exists>
/**
 * ${table.remarks}.
 */
 <#else>
 /**
  * Data Holder for the table - ${table.tableName}.
  */
</#if>
public final class ${name} {

<#list properties as property>
	<#if property.remarks?exists>
    /**
     * ${property.remarks}.
     */
	 <#else>
    /**
     * holds value of the column ${property.column.columnName}.
     */
	</#if>
    private final ${getClassName(property.dataType)} ${property.name};
	<#assign a=addImportStatement(property.dataType)>
</#list>

public ${name}(
<#assign index=1>
<#list properties as property>
<#if index != 1>
    ,
</#if>
    
	final ${getClassName(property.dataType)} the${property.name?cap_first}
    <#assign index = index + 1>
</#list>

) {

<#list properties as property>
this.${property.name} = the${property.name?cap_first};
</#list>
}

<#list properties as property>
    /**
     * gets value of column - ${property.column.columnName}.
	 *
     * @return ${property.name}
     */
    public ${getClassName(property.dataType)} get${property.name?cap_first}() {
        return ${property.name};
	}


</#list>

}
</#assign>
<@importStatements/>
${capturedOutput}
