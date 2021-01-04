<#include "base.ftl">
<#if rootPackage?? && rootPackage?length != 0 >package ${rootPackage};</#if>

<#assign capturedOutput>
public final class ${name}Manager {


    private static ${name}Manager ${name?uncap_first}Manager;

    private final javax.sql.DataSource dbDataSource;

    private final Observer observer;

    <#list orm.entities as entity>
    <#assign a=addImportStatement(entity.daoPackage + "." + entity.name + "Store")>
    private final ${entity.name}Store ${entity.name?uncap_first}Store;
    </#list>

    private ${name}Manager(final javax.sql.DataSource dbDataSource) {
        this.dbDataSource = dbDataSource;
        this.observer = new Observer();
        <#list orm.entities as entity>
        this.${entity.name?uncap_first}Store = new ${entity.name}Store(dbDataSource,this.observer
        <#list entity.sampleDistinctCustomColumnTypeProperties as property>
                                ,this::get${property.column.typeName?cap_first}
                                ,this::convert${property.column.typeName?cap_first}
        </#list>
        );
        </#list>
    }

    public static final ${name}Manager getManager(final DataSource dbDataSource) {
        if(${name?uncap_first}Manager == null) {
            ${name?uncap_first}Manager = new ${name}Manager(dbDataSource);
        }
        return ${name?uncap_first}Manager;
    }
    <#assign a=addImportStatement("javax.sql.DataSource")>
    <#list orm.entities as entity>
    public final ${entity.name}Store get${entity.name}Store() {
        return this.${entity.name?uncap_first}Store;
    }
    </#list>

<#list orm.database.distinctCustomColumnTypeNames as typeName>
    <#switch typeName>
    <#case "json">
        <#include "/template/java/custom-object/json.ftl">
        <#break>
            <#case "jsonb">
        <#include "/template/java/custom-object/jsonb.ftl">
        <#break>
        <#case "uuid">
        <#include "/template/java/custom-object/uuid.ftl">
        <#break>
         <#case "interval">
        <#include "/template/java/custom-object/interval.ftl">
        <#break>
     </#switch>
</#list>

    public class Observer {
        // Observer is intenal
        // This also prevents store creation ourtside ${name}Manager
        private Observer() {

        }
    }

    @FunctionalInterface
    public interface ConvertFunction<T, Object> {
        Object apply(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface GetFunction<ResultSet, Integer, R> {
        R apply(ResultSet t, Integer u) throws SQLException;
    }
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
}
</#assign>
<@importStatements/>
${capturedOutput}
