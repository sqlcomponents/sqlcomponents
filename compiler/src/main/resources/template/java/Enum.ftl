<#include "base.ftl">
<#if beanPackage?? && beanPackage?length != 0 >package ${beanPackage};</#if>

<#assign capturedOutput>
<#if table.remarks?exists>
/**
 * ${table.remarks}.
 */
 <#else>
 /**
  * Enum for - ${table.tableName}.
  */
</#if>
public enum ${name}Type {

<#list values as value>

    /**
     * ${value} type.
     */
    ${value}("${value?trim}")<#if !value?is_last>,<#else>;</#if>
</#list>

    /**
     * The Value.
     */
    private final String value;

    /**
     * Instantiates a new ${name} type.
     *
     * @param aValue the a value
     */
    ${name}Type(final String aValue) {
        this.value = aValue;
    }

    /**
     * Value ${name} type.
     *
     * @param aValue the a value
     * @return the ${name} type
     */
    public static ${name}Type value(final String aValue) {
        for (${name}Type ${name?uncap_first}Type : ${name}Type.values()) {
            if (${name?uncap_first}Type.value.equals(aValue)) {
                return ${name?uncap_first}Type;
            }
        }
        return null;
    }
}
</#assign>
<@importStatements/>
${capturedOutput}