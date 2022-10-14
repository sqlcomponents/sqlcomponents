<#include "/template/java/jdbcbase.ftl">
<#import "/template/java/columns.ftl" as columns>
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}</#if>;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

<#assign capturedOutput>
/**
 * Datastore for the table - ${table.tableName}.
 */
public final class ${name}Store  {

    private final javax.sql.DataSource dbDataSource;

    private final ${orm.application.name}Manager.Observer observer;
    <#assign a=addImportStatement(orm.application.rootPackage+ "." + orm.application.name + "Manager")>


    <#list sampleDistinctCustomColumnTypeProperties as property>
    <#assign a=addImportStatement(property.dataType)>
    <#assign a=addImportStatement("java.sql.ResultSet")>
    private final ${orm.application.name}Manager.GetFunction<ResultSet, Integer, ${getClassName(property.dataType)}> get${property.column.typeName?cap_first};
    private final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> convert${property.column.typeName?cap_first};
    </#list>

    <#if containsEncryptedProperty() >
        private final Function<String,String> encryptionFunction;
        private final Function<String,String> decryptionFunction;
    </#if>

    /**
     * Datastore
     */
    public ${name}Store(final javax.sql.DataSource theDataSource
                ,final ${orm.application.name}Manager.Observer theObserver
                    <#list sampleDistinctCustomColumnTypeProperties as property>
                    ,final ${orm.application.name}Manager.GetFunction<ResultSet, Integer, ${getClassName(property.dataType)}> theGet${property.column.typeName?cap_first}
                    ,final ${orm.application.name}Manager.ConvertFunction<${getClassName(property.dataType)},Object> theConvert${property.column.typeName?cap_first}
                    </#list>

                    <#if containsEncryptedProperty() >
                        <#assign a=addImportStatement("javax.sql.DataSource")>
                        ,final Function<String,String> encryptionFunction
                        ,final Function<String,String> decryptionFunction
                    </#if>
                ) {
        this.dbDataSource = theDataSource;
        this.observer = theObserver;
        <#list sampleDistinctCustomColumnTypeProperties as property>
        this.get${property.column.typeName?cap_first} =  theGet${property.column.typeName?cap_first};
        this.convert${property.column.typeName?cap_first} =  theConvert${property.column.typeName?cap_first};
        </#list>
        <#if containsEncryptedProperty() >
            this.encryptionFunction = encryptionFunction;
            this.decryptionFunction = decryptionFunction;
        </#if>
    }

	<#list orm.methodSpecification as method>
		<#include "/template/java/method/${method}.ftl">
	</#list>



	<#--
	<#if exportedKeys?size != 0>
	public List<${name}> get${name}s(Search${name} search${name}) throws SQLException;
		<#assign a=addImportStatement(javaPackageName+ ".search.Search" + name)>
	</#if>	
	-->	

	private ${name} rowMapper(ResultSet rs) throws SQLException {
        final ${name} ${name?uncap_first} = new ${name}();<#assign index=1>
		<#list properties as property>
		<#switch property.dataType>
          <#case "java.time.LocalDate">
            ${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}) == null ? null : rs.get${getJDBCClassName(property.dataType)}(${index}).toLocalDate());
        	 <#break>
          <#case "java.time.LocalTime">
        	 ${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}) == null ? null : rs.get${getJDBCClassName(property.dataType)}(${index}).toLocalTime());
              <#break>
           <#case "java.time.LocalDateTime">
             ${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}) == null ? null : rs.get${getJDBCClassName(property.dataType)}(${index}).toLocalDateTime());
          <#break>
          <#case "java.nio.ByteBuffer">
             ${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}) == null ? null : ByteBuffer.wrap(rs.get${getJDBCClassName(property.dataType)}(${index})));
          <#break>
          <#case "java.lang.Character">
          	 ${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}) == null ? null : rs.get${getJDBCClassName(property.dataType)}(${index}).charAt(0));
           <#break>
        	   <#case "org.json.JSONObject">
        	    ${name?uncap_first}.set${property.name?cap_first}(this.get${property.column.typeName?cap_first}.apply(rs,${index}));
                 <#break>
           <#case "java.util.UUID">
        	    ${name?uncap_first}.set${property.name?cap_first}(this.get${property.column.typeName?cap_first}.apply(rs,${index}));
                 <#break>
                        <#case "java.time.Duration">
        	    ${name?uncap_first}.set${property.name?cap_first}(this.get${property.column.typeName?cap_first}.apply(rs,${index}));
                 <#break>
          <#default>
          ${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}));
          <#break>
        </#switch>
		<#assign index = index + 1>
		</#list>
        return ${name?uncap_first};
    }



<#list properties as property>
<#assign a=addImportStatement(property.dataType)>

    public static Value<Column.${property.name?cap_first}Column,${getClassName(property.dataType)}> ${property.name}(final ${getClassName(property.dataType)} value) {
        return new Value<>(${property.name}(),value);
    }
    
    public static Column.${property.name?cap_first}Column ${property.name}() {
        return new WhereClause().${property.name}();
    }

</#list>

    public static class WhereClause  extends PartialWhereClause  {
        private WhereClause(){
        }
        private String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                String asSql;
                if (node instanceof Column) {
                    asSql = ((Column) node).asSql();
                } else if (node instanceof WhereClause) {
                    asSql = "(" + ((WhereClause) node).asSql() + ")";
                } else {
                    asSql = (String) node;
                }
                return asSql;
            }).collect(Collectors.joining(" "));
        }

        public PartialWhereClause and() {
            this.nodes.add("AND");
            return this;
        }

        public PartialWhereClause  or() {
            this.nodes.add("OR");
            return this;
        }

        public WhereClause  and(final WhereClause  whereClause) {
            this.nodes.add("AND");
            this.nodes.add(whereClause);
            return (WhereClause) this;
        }

        public WhereClause  or(final WhereClause  whereClause) {
            this.nodes.add("OR");
            this.nodes.add(whereClause);
            return (WhereClause) this;
        }
    }

    public static class PartialWhereClause  {

        protected final List<Object> nodes;

        private PartialWhereClause() {
            this.nodes = new ArrayList<>();
        }
<#list properties as property>

        public Column.${property.name?cap_first}Column ${property.name}() {
            Column.${property.name?cap_first}Column query = new Column.${property.name?cap_first}Column(this);
            this.nodes.add(query);
            return query;
        }

		</#list>

       

        

    }

    public static class Value<T extends Column<R>,R> {
        private final T column;
        private final R value;

        private Value(final T column,final R value) {
            this.column =column;
            this.value = value;
        }
    }


    public static abstract class Column<T> {

  
            private final PartialWhereClause  whereClause ;

            protected Column(final PartialWhereClause  whereClause) {
                this.whereClause  = whereClause ;
            }

            protected WhereClause  getWhereClause() {
                return (WhereClause) whereClause ;
            }

            protected abstract String asSql();

            protected abstract Boolean validate(T value);

            <#list properties as property>
    <#switch property.dataType>
    <#case "java.lang.String">
        <@columns.StringColumn property=property/>
        <#break>
    <#case "java.lang.Character">
        <@columns.CharacterColumn property=property/>
        <#break>
    <#case "java.lang.Integer">
        <@columns.IntegerColumn property=property/>
        <#break>
    <#case "java.lang.Short">
        <@columns.ShortColumn property=property/>
        <#break>
    <#case "java.lang.Byte">
        <@columns.ByteColumn property=property/>
        <#break>
    <#case "java.lang.Long">
        <@columns.LongColumn property=property/>
        <#break>
    <#case "java.lang.Float">
        <@columns.FloatColumn property=property/>
        <#break>
    <#case "java.lang.Double">
        <@columns.DoubleColumn property=property/>
        <#break>
    <#case "java.lang.Boolean">
        <@columns.BooleanColumn property=property/>
        <#break>
    <#case "java.time.LocalDate">
        <@columns.LocalDateColumn property=property/>
        <#break>
    <#case "java.time.LocalTime">
        <@columns.LocalTimeColumn property=property/>
        <#break>
    <#case "java.time.LocalDateTime">
        <@columns.LocalDateTimeColumn property=property/>
        <#break>
    <#case "java.util.UUID">
        <@columns.UUIDColumn property=property/>
        <#break>
    <#case "org.json.JSONObject" >
        <@columns.JSONObjectColumn property=property/>
        <#break>
    <#case "java.nio.ByteBuffer" >
        <@columns.ByteBuffer property=property/>
        <#break>
    <#case "java.time.Duration" >
        <@columns.DurationColumn property=property/>
        <#break>    
    </#switch>
		</#list>

        }
    

}<#assign a=addImportStatement("java.util.ArrayList")><#assign a=addImportStatement("java.time.LocalDate")>
</#assign>
<@importStatements/>

${capturedOutput}