<#include "/template/java/jdbcbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}</#if>;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

<#assign capturedOutput>
/**
 * Datastore for the table - ${table.tableName}.
 */
public final class ${name}Store${orm.daoSuffix}  {

    private final DataSource dataSource;

    /**
     * Datastore
     */
    public ${name}Store${orm.daoSuffix}(final DataSource dataSource) {
        this.dataSource = dataSource;
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
		<#assign rsGet = "rs.get" + getJDBCClassName(property.dataType) + "("+ index + ")" >
		${name?uncap_first}.set${property.name?cap_first}(${wrapGet(rsGet,property)});<#assign index = index + 1>
		</#list>
        return ${name?uncap_first};
    }



<#list properties as property>
    <#switch property.dataType>
    <#case "java.lang.String">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>StringField ${property.name}() {
    <#break>
    <#case "java.lang.Character">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>CharacterField ${property.name}() {
    <#break>
    <#case "java.lang.Integer">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>NumberField<Integer> ${property.name}() {
    <#break>
    <#case "java.lang.Short">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>NumberField<Short> ${property.name}() {
    <#break>
    <#case "java.lang.Byte">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>NumberField<Byte> ${property.name}() {
    <#break>
    <#case "java.lang.Long">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>NumberField<Long> ${property.name}() {
    <#break>
    <#case "java.lang.Float">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>NumberField<Float> ${property.name}() {
    <#break>
    <#case "java.lang.Double">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>NumberField<Double> ${property.name}() {
    <#break>
    <#case "java.lang.Boolean">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>BooleanField ${property.name}() {
    <#break>
    <#case "java.time.LocalDate">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>DateField ${property.name}() {
    <#break>
    <#case "java.time.LocalTime">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>TimeField ${property.name}() {
    <#break>
    <#case "java.time.LocalDateTime">
    public static PartialWhereClause .<#if property.column.isNullable??>Nullable</#if>DateTimeField ${property.name}() {
    <#break>
    </#switch>
        return new WhereClause().${property.name}();
    }
    </#list>

    private static class WhereClause  extends PartialWhereClause  {
        private String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                String asSql;
                if (node instanceof Field) {
                    asSql = ((Field) node).asSql();
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

    private static class PartialWhereClause  {

        protected final List<Object> nodes;

        private PartialWhereClause () {
            this.nodes = new ArrayList<>();
        }
<#list properties as property>
    <#switch property.dataType>
    <#case "java.lang.String">
        public <#if property.column.isNullable??>Nullable</#if>StringField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>StringField query = new <#if property.column.isNullable??>Nullable</#if>StringField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Character">
        public <#if property.column.isNullable??>Nullable</#if>CharacterField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>CharacterField query = new <#if property.column.isNullable??>Nullable</#if>CharacterField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Integer">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Integer> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Integer> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Short">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Short> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Short> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Byte">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Byte> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Byte> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Long">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Long> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Long> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Float">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Float> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Float> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Double">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Double> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Double> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Boolean">
        public <#if property.column.isNullable??>Nullable</#if>BooleanField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>BooleanField query = new <#if property.column.isNullable??>Nullable</#if>BooleanField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.time.LocalDate">
        public <#if property.column.isNullable??>Nullable</#if>DateField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>DateField query = new <#if property.column.isNullable??>Nullable</#if>DateField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.time.LocalTime">
        public <#if property.column.isNullable??>Nullable</#if>TimeField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>TimeField query = new <#if property.column.isNullable??>Nullable</#if>TimeField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.time.LocalDateTime">
        public <#if property.column.isNullable??>Nullable</#if>DateTimeField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>DateTimeField query = new <#if property.column.isNullable??>Nullable</#if>DateTimeField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    </#switch>
		</#list>

        public abstract class Field {

            protected final String columnName;
            private final PartialWhereClause  whereClause ;

            public Field(final String columnName, final PartialWhereClause  whereClause) {
                this.columnName = columnName;
                this.whereClause  = whereClause ;
            }

            protected WhereClause  getWhereClause () {
                return (WhereClause) whereClause ;
            }

            protected abstract String asSql();

        }

        public class StringField extends Field {
            protected String sql;

            public StringField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final String value) {
                sql = columnName + "='" + value + "'";
                return getWhereClause ();
            }

            public WhereClause  like(final String value) {
                sql = columnName + " LIKE '" + value + "'";
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }



        public class NullableStringField extends StringField {

            public NullableStringField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }




        public class CharacterField extends Field {
            protected String sql;

            public CharacterField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final String value) {
                sql = columnName + "='" + value + "'";
                return getWhereClause ();
            }

            public WhereClause  like(final String value) {
                sql = columnName + " LIKE '" + value + "'";
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }



        public class NullableCharacterField extends CharacterField {

            public NullableCharacterField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }



        public class BooleanField extends Field {
            protected String sql;

            public BooleanField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final Boolean value) {
                sql = columnName + "=" + value ;
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableBooleanField extends BooleanField {

            public NullableBooleanField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }

        public class NumberField<T extends Number> extends Field {

            protected String sql;

            public NumberField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final T value) {
                sql = columnName + "=" + value;
                return getWhereClause ();
            }

            public WhereClause  gt(final T value) {
                sql = columnName + ">" + value;
                return getWhereClause ();
            }

            public WhereClause  gte(final T value) {
                sql = columnName + ">=" + value;
                return getWhereClause ();
            }

            public WhereClause  lt(final T value) {
                sql = columnName + "<" + value;
                return getWhereClause ();
            }

            public WhereClause  lte(final T value) {
                sql = columnName + "<=" + value;
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableNumberField<T extends Number> extends NumberField<T> {

            public NullableNumberField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }

        public class DateField<LocalDate> extends Field {
        
            protected String sql;

            public DateField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final LocalDate value) {
                sql = columnName + "=" + value;
                return getWhereClause ();
            }

            public WhereClause  gt(final LocalDate value) {
                sql = columnName + ">" + value;
                return getWhereClause ();
            }

            public WhereClause  gte(final LocalDate value) {
                sql = columnName + ">=" + value;
                return getWhereClause ();
            }

            public WhereClause  lt(final LocalDate value) {
                sql = columnName + "<" + value;
                return getWhereClause ();
            }

            public WhereClause  lte(final LocalDate value) {
                sql = columnName + "<=" + value;
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableDateField extends DateField {

            public NullableDateField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }

        public class TimeField<LocalTime> extends Field {

            protected String sql;

            public TimeField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final LocalTime value) {
                sql = columnName + "=" + value;
                return getWhereClause ();
            }

            public WhereClause  gt(final LocalTime value) {
                sql = columnName + ">" + value;
                return getWhereClause ();
            }

            public WhereClause  gte(final LocalTime value) {
                sql = columnName + ">=" + value;
                return getWhereClause ();
            }

            public WhereClause  lt(final LocalTime value) {
                sql = columnName + "<" + value;
                return getWhereClause ();
            }

            public WhereClause  lte(final LocalTime value) {
                sql = columnName + "<=" + value;
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableTimeField extends TimeField {

            public NullableTimeField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }


        public class DateTimeField<LocalDateTime> extends Field {

            protected String sql;

            public DateTimeField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final LocalDateTime value) {
                sql = columnName + "=" + value;
                return getWhereClause ();
            }

            public WhereClause  gt(final LocalDateTime value) {
                sql = columnName + ">" + value;
                return getWhereClause ();
            }

            public WhereClause  gte(final LocalDateTime value) {
                sql = columnName + ">=" + value;
                return getWhereClause ();
            }

            public WhereClause  lt(final LocalDateTime value) {
                sql = columnName + "<" + value;
                return getWhereClause ();
            }

            public WhereClause  lte(final LocalDateTime value) {
                sql = columnName + "<=" + value;
                return getWhereClause ();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableDateTimeField extends DateTimeField {

            public NullableDateTimeField(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause ();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause ();
            }
        }

    }

}<#assign a=addImportStatement("java.util.ArrayList")><#assign a=addImportStatement("java.time.LocalDate")>
</#assign>
<@importStatements/>

${capturedOutput}