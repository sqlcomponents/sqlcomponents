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
    private ${getClassName(property.dataType)} ${property.name};
	<#assign a=addImportStatement(property.dataType)>
</#list>
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
	 * sets value of column - ${property.column.columnName}.
	 *
	 * @param the${property.name?cap_first}
	 */
	public void set${property.name?cap_first}(final ${getClassName(property.dataType)} the${property.name?cap_first}) {
		this.${property.name} = the${property.name?cap_first};
	}
</#list>

}
</#assign>
<@importStatements/>
${capturedOutput}
