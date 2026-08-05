<#include "base.ftl">

<#assign a=addImportStatement("java.sql.Connection")>
<#assign a=addImportStatement("java.util.ArrayList")>

<#if rootPackage?? && rootPackage?length != 0 >package ${rootPackage};</#if>

<#assign capturedOutput>
<#include "SqlBuilder.ftl">
<@sqlBuilderRegisterImports/>
public final class DataManager {
    /**
    * dataManager variable.
    */
    private static DataManager dataManager;

    /**
    * dataSource variable.
    */
    private DataSource dataSource;

    /**
    * observer variable.
    */
    private final Observer observer;
    /**
    * procedure variable.
    */
    private final Procedure procedure;

    <#list orm.entities as entity>
    <#if !entity.type?? >
    <#assign a=addImportStatement(entity.daoPackage + "." + entity.name + "Store")>
    /**
    * ${entity.name?uncap_first}Store variable.
    */
    private final ${entity.name}Store ${entity.name?uncap_first}Store;
    </#if>
    </#list>

    private DataManager(
     final DataSource dataSource
     <#if encryption?has_content >
     , final Function<String, String> encryptionFunction
     , final Function<String, String> decryptionFunction
      <#assign a=addImportStatement("java.util.function.Function")>
     </#if>
    ) {
        this.dataSource = dataSource;
        this.observer = new Observer();
        this.procedure = new Procedure(this);
        <#list orm.entities as entity>
        <#if !entity.type?? >
        this.${entity.name?uncap_first}Store = ${entity.name}Store
.get${entity.name}Store(this, this.observer<#if entity.containsEncryptedProperty() >,
             encryptionFunction,
             decryptionFunction
        </#if>);
        </#if>
        </#list>
    }

    /**
     * getManager method with DataSource.
     * @param dataSource
     <#if encryption?has_content>
     * @param encryptionFunction
     * @param decryptionFunction
     </#if>
     * @return dataManager
     */
    public static DataManager getManager(
        final DataSource dataSource
        <#if encryption?has_content>
        , final Function<String, String> encryptionFunction
        , final Function<String, String> decryptionFunction
        </#if>
    ) {
        if (dataManager == null) {
            dataManager = new DataManager(
                dataSource
                <#if encryption?has_content>
                , encryptionFunction
                , decryptionFunction
                </#if>
            );
        } else if (dataSource != null && dataManager.dataSource == null) {
            dataManager.dataSource = dataSource;
        }
        return dataManager;
    }

    /**
     * getManager method without DataSource.
     <#if encryption?has_content>
     * @param encryptionFunction
     * @param decryptionFunction
     </#if>
     * @return dataManager
     */
    public static DataManager getManager(
        <#if encryption?has_content>
        final Function<String, String> encryptionFunction
        , final Function<String, String> decryptionFunction
        </#if>
    ) {
        return getManager(
            null
            <#if encryption?has_content>
            , encryptionFunction
            , decryptionFunction
            </#if>
        );
    }

    <#if encryption?has_content>
    /**
     * getManager method with DataSource only.
     * @param dataSource
     * @return dataManager
     */
    public static DataManager getManager(final DataSource dataSource) {
        return getManager(dataSource, null, null);
    }

    /**
     * getManager method with no arguments.
     * @return dataManager
     */
    public static DataManager getManager() {
        return getManager(null, null, null);
    }
    </#if>

    /**
     * Retrieves default DataSource.
     * @return dataSource
     * @throws IllegalStateException if dataSource is null
     */
    public DataSource getDataSource() {
        if (this.dataSource == null) {
            throw new IllegalStateException("Default DataSource is not configured in DataManager. Pass DataSource to DataManager.getManager(dataSource, ...) or invoke statement with explicit DataSource parameter.");
        }
        return this.dataSource;
    }

    /**
     * Sets default DataSource.
     * @param dataSource
     */
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }


    <#assign a=addImportStatement("javax.sql.DataSource")>
    <#list orm.entities as entity>
    <#if !entity.type?? >
    /**
     * ${entity.name}Store.
     * @return ${entity.name?uncap_first}Store
     */
    public ${entity.name}Store get${entity.name}Store() {
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
    public interface Column<T> {
      /**
       * String name.
       * @return name
       */
        String name();
       /**
        * String asSql.
        * @return name
        */
        String asSql();
         /**
         * String validate.
         * @param value
         * @return name
         */
        boolean validate(T value);
       /**
        *  set method.
        * @param preparedStatement
        * @param value
        * @throws SQLException
        */
        void set(SqlBuilder.PreparedSqlBuilder preparedStatement,
        T value) ;
        /**
         *  T get method.
         * @param resultSet
         * @param i
         * @return get
         * @throws SQLException
         */
        T get(ResultSet resultSet, int i) throws SQLException;

    }
     /**
     *  Value Class.
     * @param <T>
     * @param <R>
     */
    public static class Value<T extends Column<R>, R> {

    /**
    * column variable.
    */
        private final T column;
    /**
    * value variable.
    */
        private final R value;
        /**
        * Value method.
        * @param columnT
        * @param valueR
        */
        public Value(final T columnT, final R valueR) {
            this.column = columnT;
            this.value = valueR;
        }
         /**
         * T column method.
         * @return column
         */
        public T column() {
            return column;
        }
          /**
          * set method.
          * @param preparedStatement
          * @throws SQLException
          */
        public void set(final SqlBuilder.PreparedSqlBuilder preparedStatement) {
            column.set(preparedStatement, value);
        }
    }

    public final class Observer {
        // Observer is internal
        // This also prevents store creation outside DataManager
        private Observer() {

        }
    }
    <#assign a=addImportStatement("java.sql.ResultSet")>
    <#assign a=addImportStatement("java.sql.SQLException")>
    <#assign a=addImportStatement("java.util.List")>

    <#if orm.hasJavaClass("org.springframework.data.domain.Page") >
    //
    <#else>
    /**
     * Page method.
     * @param content
     * @param totalElements
     * @return Page
     * @param <T>
     */
    public static <T> Page<T> page(final List<T> content,
    final int totalElements) {
        return new Page<>(content, totalElements);
    }

    public static final class Page<T> {
    /**
    *   content variable.
    */
        private final List<T> content;
     /**
     *   totalElements variable.
     */
        private final int totalElements;

        private Page(final List<T> contentT, final int totalElementsI) {
            this.content = contentT;
            this.totalElements = totalElementsI;
        }
            /**
            * getContent method.
            * @return content
            */
        public List<T> getContent() {
            return content;
        }
        /**
         * getTotalElements method.
         * @return totalElements
         */
        public int getTotalElements() {
            return totalElements;
        }
    }
    </#if>

    <#include "Procedures.ftl">

    <#include "query/SelectQuery.ftl">
    <#include "query/Statement.ftl">

    <#include "clause/WhereClause.ftl">

    <#include "method/DeleteStatement.ftl">

    <@sqlBuilderNestedClass/>

}
</#assign>
<@importStatements/>
${capturedOutput}
