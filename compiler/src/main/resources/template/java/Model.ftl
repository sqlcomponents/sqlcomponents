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
    /**
<#list properties as property>
    *@param ${property.name}S ${getClassName(property.dataType)}.
        </#list>
  */
    public ${name}(<#list properties as property>
    final ${getClassName(property.dataType)} ${property.name}S<#if !property?is_last>,</#if>
        </#list>
    ) {
        <#list properties as property>
        this.${property.name} = ${property.name}S;
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
    /**
     * gets value of column - the${property.name?cap_first}.
	 * @param the${property.name?cap_first}
     * @return the${property.name?cap_first}
     */
    public ${name} with${property.name?cap_first}(final ${getClassName(property.dataType)} the${property.name?cap_first}) {
        return new ${name}(
            <#assign index=1>
            <#list properties as iProperty
                ><#if index != 1
                >,
                </#if><#if property.name == iProperty.name>the${property.name?cap_first}<#else>${iProperty.name}</#if
                ><#assign index = index + 1
                ></#list>);
    }

</#list>

}
</#assign>
<@importStatements/>
${capturedOutput}