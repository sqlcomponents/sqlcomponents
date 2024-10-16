<#include "base.ftl">
<#if beanPackage?? && beanPackage?length != 0 > package ${beanPackage};</#if>

<#assign capturedOutput>
<#if table.remarks?exists>
/**
 * ${table.remarks}.
 <#else>
 /**
  * Data Holder for the table - ${table.tableName}.
</#if>
<#list properties as property><#assign a=addImportStatement(property.dataType)>
    *@param ${property.name} ${getClassName(property.dataType)}.
</#list>
 */
public record ${name}(<#list properties as property>
    ${getClassName(property.dataType)} ${property.name}<#if !property?is_last>,</#if>
        </#list>
    ) {

        <#list properties as property>

    /**
    * gets value of column - the${property.name?cap_first}.
    * @param the${property.name?cap_first}
    * @return the${property.name?cap_first}
    */
    public ${name} with${property.name?cap_first}(final ${getClassName(property.dataType)} the${property.name?cap_first}) {
        return new ${name}(<#assign index=1>
                <#list properties as iProperty><#if index != 1>,
                </#if><#if property.name == iProperty.name>the${property.name?cap_first}<#else>${iProperty.name}</#if
                ><#assign index = index + 1
                ></#list>);
    }

</#list>
    }




</#assign>
<@importStatements/>
${capturedOutput}