<#include "base.ftl">
<#if rootPackage?? && rootPackage?length != 0 >package ${rootPackage};</#if>

<#assign capturedOutput>
public final class DataManager {

    <#if !multipleManagers>
    private static DataManager dataManager;
    </#if>


    private final Observer observer;

    private final Procedure procedure;

    <#list orm.entities as entity>
    <#if !entity.type?? >
    <#assign a=addImportStatement(entity.daoPackage + "." + entity.name + "Store")>
    private final ${entity.name}Store ${entity.name?uncap_first}Store;
    </#if>
    </#list>

<#if multipleManagers>public <#else>private</#if> DataManager(final javax.sql.DataSource dbDataSource
    <#if encryption?has_content  >
    <#assign a=addImportStatement("java.util.function.Function")>
    ,final Function<String,String> encryptionFunction
    ,final Function<String,String> decryptionFunction
    </#if>
    ) {
        this.observer = new Observer();
        this.procedure = new Procedure(dbDataSource);
        
        <#list orm.entities as entity>
        <#if !entity.type?? >

        this.${entity.name?uncap_first}Store = ${entity.name}Store.get${entity.name}Store(dbDataSource,this.observer
        
        <#if  entity.containsEncryptedProperty() >
            ,encryptionFunction
            , decryptionFunction
        </#if>
        );
        </#if>
        </#list>
    }
    <#if !multipleManagers>
    public static final DataManager getManager(final DataSource dbDataSource
    <#if encryption?has_content  >
    <#assign a=addImportStatement("javax.sql.DataSource")>
    ,final Function<String,String> encryptionFunction
    ,final Function<String,String> decryptionFunction
    </#if>
                                                            ) {
        if(dataManager == null) {
            dataManager = new DataManager(dbDataSource
            <#if encryption?has_content  >
            
            , encryptionFunction
            ,decryptionFunction
            </#if>
            );
        }
        return dataManager;
    }
</#if>

    <#assign a=addImportStatement("javax.sql.DataSource")>
    <#list orm.entities as entity>
    <#if !entity.type?? >
    public final ${entity.name}Store get${entity.name}Store() {
        return this.${entity.name?uncap_first}Store;
    }
    </#if>
    </#list>

<#list orm.database.distinctCustomColumnTypeNames as typeName>
    <#switch typeName>

  
        <#case "macaddr8">
            <#assign a=addImportStatement("org.postgresql.util.PGobject")>
            <#break>
        <#case "macaddr">
            <#assign a=addImportStatement("org.postgresql.util.PGobject")>
            <#break>
        <#case "path">
            <#assign a=addImportStatement("org.postgresql.util.PGobject")>
            <#break>  
  
     </#switch>
</#list>
    <#assign a=addImportStatement("java.sql.PreparedStatement")>
    public interface Column<T> {
        String name();

        String asSql();

        boolean validate(T value);

        void set(final PreparedStatement preparedStatement, final int i, final T value) throws SQLException;

        T get(final ResultSet resultSet, final int i) throws SQLException;

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
        // This also prevents store creation outside DataManager
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
        private final List<T> content;
        private final int totalElements;

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

    <#include "Procedures.ftl">

}
</#assign>
<@importStatements/>
${capturedOutput}
