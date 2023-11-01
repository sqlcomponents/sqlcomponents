<#include "base.ftl">
<#if rootPackage?? && rootPackage?length != 0 >package ${rootPackage};</#if>

<#assign capturedOutput>
public final class ${name}Manager {

    <#if !multipleManagers>
    private static ${name}Manager ${name?uncap_first}Manager;
    </#if>

    private final javax.sql.DataSource dbDataSource;

    private final Observer observer;

    <#list orm.entities as entity>
    <#assign a=addImportStatement(entity.daoPackage + "." + entity.name + "Store")>
    private final ${entity.name}Store ${entity.name?uncap_first}Store;
    </#list>

<#if multipleManagers>public <#else>private</#if> ${name}Manager(final javax.sql.DataSource dbDataSource
    <#if encryption?has_content  >
    <#assign a=addImportStatement("java.util.function.Function")>
    ,final Function<String,String> encryptionFunction
    ,final Function<String,String> decryptionFunction
    </#if>
    ) {
        this.dbDataSource = dbDataSource;
        this.observer = new Observer();
        <#list orm.entities as entity>
        this.${entity.name?uncap_first}Store = ${entity.name}Store.get${entity.name}Store(dbDataSource,this.observer
        <#list entity.sampleDistinctCustomColumnTypeProperties as property>
                    ,this::get${property.column.typeName?cap_first}
                    ,this::convert${property.column.typeName?cap_first}
        </#list>
        <#if entity.containsEncryptedProperty() >
            ,encryptionFunction
            , decryptionFunction
        </#if>
        );
        </#list>
    }
    <#if !multipleManagers>
    public static final ${name}Manager getManager(final DataSource dbDataSource
    <#if encryption?has_content  >
    <#assign a=addImportStatement("javax.sql.DataSource")>
    ,final Function<String,String> encryptionFunction
    ,final Function<String,String> decryptionFunction
    </#if>
                                                            ) {
        if(${name?uncap_first}Manager == null) {
            ${name?uncap_first}Manager = new ${name}Manager(dbDataSource
            <#if encryption?has_content  >
            
            , encryptionFunction
            ,decryptionFunction
            </#if>
            );
        }
        return ${name?uncap_first}Manager;
    }
</#if>

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
    <#assign a=addImportStatement("java.sql.PreparedStatement")>
    public static abstract interface Column<T> {
        String name();

        String asSql();

        boolean validate(T value);

        void set(final PreparedStatement preparedStatement, final int i, final T value) throws SQLException;

    }

    public static class Value<T extends Column<R>,R> {
        private final T column;
        private final R value;

        public Value(final T column,final R value) {
            this.column =column;
            this.value = value;
        }

        public T column() {
            return column;
        }

        public void set(final PreparedStatement preparedStatement, final int i) throws SQLException{
            column.set(preparedStatement,i,value);
        }
    }

    public class Observer
    {
        // Observer is internal
        // This also prevents store creation outside ${name}Manager
        private Observer() {

        }
    }

    @FunctionalInterface
    public interface ConvertFunction<T, Object>
    {
        Object apply(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface GetFunction<ResultSet, Integer, R>
    {
        R apply(ResultSet t, Integer u) throws SQLException;
    }
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
    <#assign a=addImportStatement("java.util.List")>

    <#if orm.hasJavaClass("org.springframework.data.domain.Page") >
    //
    <#else>

    public static <T> Page<T> page(List<T> content, int totalElements) {
        return new Page(content,totalElements);
    }

    public static class Page<T> {
        final List<T> content;
        final int totalElements;

        private Page(List<T> content, int totalElements) {
            this.content = content;
            this.totalElements = totalElements;
        }

        public List<T> getContent() {
            return content;
        }

        public int getTotalElements() {
            return totalElements;
        }
    }
    </#if>

}
</#assign>
<@importStatements/>
${capturedOutput}
