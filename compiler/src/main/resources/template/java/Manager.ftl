<#include "base.ftl">
<#if rootPackage?? && rootPackage?length != 0 >package ${rootPackage};</#if>

<#assign capturedOutput>
public final class DataManager {
    /**
    * dataManager variable.
    */
    <#if !multipleManagers>
    private static DataManager dataManager;
    </#if>

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

<#if multipleManagers>public <#else>private</#if> DataManager(final javax.sql.DataSource dbDataSource<#if encryption?has_content >,
     final Function<String, String> encryptionFunction,
     final Function<String, String> decryptionFunction
      <#assign a=addImportStatement("java.util.function.Function")>
    </#if>
    ) {
        this.observer = new Observer();
        this.procedure = new Procedure(dbDataSource);
        <#list orm.entities as entity>
        <#if !entity.type?? >
        this.${entity.name?uncap_first}Store = ${entity.name}Store
.get${entity.name}Store(dbDataSource, this.observer<#if entity.containsEncryptedProperty() >,
             encryptionFunction,
             decryptionFunction
        </#if>);
        </#if>
        </#list>
    }
     /**
     * getManager method.
     * @param dbDataSource
     * @param encryptionFunction
     * @param decryptionFunction
     * @return dataManager
     */
    <#if !multipleManagers>
    public static DataManager getManager(final DataSource dbDataSource<#if encryption?has_content>,
    <#assign a=addImportStatement("javax.sql.DataSource")>
     final Function<String, String> encryptionFunction,
     final Function<String, String> decryptionFunction
    </#if>
                                                            ) {
        if (dataManager == null) {
            dataManager = new DataManager(dbDataSource<#if encryption?has_content>,
             encryptionFunction,
             decryptionFunction
            </#if>
            );
        }
        return dataManager;
    }
</#if>

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
    <#assign a=addImportStatement("java.sql.PreparedStatement")>
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
        * @param i
        * @param value
        * @throws SQLException
        */
        void set(PreparedStatement preparedStatement,
         int i, T value) throws SQLException;
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
          * @param i
          * @throws SQLException
          */
        public void set(final PreparedStatement preparedStatement,
final int i) throws SQLException {
            column.set(preparedStatement, i, value);
        }
    }

    public final class Observer {
        // Observer is internal
        // This also prevents store creation outside DataManager
        private Observer() {

        }
    }

    @FunctionalInterface
    public interface ConvertFunction<T, Object> {

        /**
         * apply method.
         * @param t
         * @return apply
         * @throws SQLException
         */
        Object apply(T t) throws SQLException;
    }
         /**
          * GetFunction method.
          * @param <ResultSet>
          * @param <Integer>
          * @param <R>
          */
    @FunctionalInterface
    public interface GetFunction<ResultSet, Integer, R> {
           /**
            * R apply interface.
            * @param t
            * @param u
            * @return apply
            * @throws SQLException
            */

        R apply(ResultSet t, Integer u) throws SQLException;
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
        return new Page(content, totalElements);
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

}
</#assign>
<@importStatements/>
${capturedOutput}
