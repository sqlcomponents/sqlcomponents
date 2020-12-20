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
    public ${name}Store${orm.daoSuffix}(final DataSource theDataSource) {
        this.dataSource = theDataSource;
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
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>StringColumn ${property.name}() {
    <#break>
    <#case "java.lang.Character">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>CharacterColumn ${property.name}() {
    <#break>
    <#case "java.lang.Integer">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>NumberColumn<Integer> ${property.name}() {
    <#break>
    <#case "java.lang.Short">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>NumberColumn<Short> ${property.name}() {
    <#break>
    <#case "java.lang.Byte">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>NumberColumn<Byte> ${property.name}() {
    <#break>
    <#case "java.lang.Long">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>NumberColumn<Long> ${property.name}() {
    <#break>
    <#case "java.lang.Float">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>NumberColumn<Float> ${property.name}() {
    <#break>
    <#case "java.lang.Double">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>NumberColumn<Double> ${property.name}() {
    <#break>
    <#case "java.lang.Boolean">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>BooleanColumn ${property.name}() {
    <#break>
    <#case "java.time.LocalDate">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>DateColumn ${property.name}() {
    <#break>
    <#case "java.time.LocalTime">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>TimeColumn ${property.name}() {
    <#break>
    <#case "java.time.LocalDateTime">
    public static PartialWhereClause.<#if property.column.isNullable??>Nullable</#if>DateTimeColumn ${property.name}() {
    <#break>
    </#switch>
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
    <#switch property.dataType>
    <#case "java.lang.String">
        public <#if property.column.isNullable??>Nullable</#if>StringColumn ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>StringColumn query = new <#if property.column.isNullable??>Nullable</#if>StringColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Character">
        public <#if property.column.isNullable??>Nullable</#if>CharacterColumn ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>CharacterColumn query = new <#if property.column.isNullable??>Nullable</#if>CharacterColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Integer">
        public <#if property.column.isNullable??>Nullable</#if>NumberColumn<Integer> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberColumn<Integer> query = new <#if property.column.isNullable??>Nullable</#if>NumberColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Short">
        public <#if property.column.isNullable??>Nullable</#if>NumberColumn<Short> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberColumn<Short> query = new <#if property.column.isNullable??>Nullable</#if>NumberColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Byte">
        public <#if property.column.isNullable??>Nullable</#if>NumberColumn<Byte> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberColumn<Byte> query = new <#if property.column.isNullable??>Nullable</#if>NumberColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Long">
        public <#if property.column.isNullable??>Nullable</#if>NumberColumn<Long> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberColumn<Long> query = new <#if property.column.isNullable??>Nullable</#if>NumberColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Float">
        public <#if property.column.isNullable??>Nullable</#if>NumberColumn<Float> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberColumn<Float> query = new <#if property.column.isNullable??>Nullable</#if>NumberColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Double">
        public <#if property.column.isNullable??>Nullable</#if>NumberColumn<Double> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberColumn<Double> query = new <#if property.column.isNullable??>Nullable</#if>NumberColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Boolean">
        public <#if property.column.isNullable??>Nullable</#if>BooleanColumn ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>BooleanColumn query = new <#if property.column.isNullable??>Nullable</#if>BooleanColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.time.LocalDate">
        public <#if property.column.isNullable??>Nullable</#if>DateColumn ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>DateColumn query = new <#if property.column.isNullable??>Nullable</#if>DateColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.time.LocalTime">
        public <#if property.column.isNullable??>Nullable</#if>TimeColumn ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>TimeColumn query = new <#if property.column.isNullable??>Nullable</#if>TimeColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.time.LocalDateTime">
        public <#if property.column.isNullable??>Nullable</#if>DateTimeColumn ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>DateTimeColumn query = new <#if property.column.isNullable??>Nullable</#if>DateTimeColumn("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    </#switch>
		</#list>

        public abstract class Column {

            protected final String columnName;
            private final PartialWhereClause  whereClause ;

            public Column(final String columnName, final PartialWhereClause  whereClause) {
                this.columnName = columnName;
                this.whereClause  = whereClause ;
            }

            protected WhereClause  getWhereClause() {
                return (WhereClause) whereClause ;
            }

            protected abstract String asSql();

        }

        public class StringColumn extends Column {
            protected String sql;

            public StringColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final String value) {
                sql = columnName + "='" + value + "'";
                return getWhereClause();
            }

            public WhereClause  like(final String value) {
                sql = columnName + " LIKE '" + value + "'";
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }



        public class NullableStringColumn extends StringColumn {

            public NullableStringColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }




        public class CharacterColumn extends Column {
            protected String sql;

            public CharacterColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final String value) {
                sql = columnName + "='" + value + "'";
                return getWhereClause();
            }

            public WhereClause  like(final String value) {
                sql = columnName + " LIKE '" + value + "'";
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }



        public class NullableCharacterColumn extends CharacterColumn {

            public NullableCharacterColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }



        public class BooleanColumn extends Column {
            protected String sql;

            public BooleanColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final Boolean value) {
                sql = columnName + "=" + value ;
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableBooleanColumn extends BooleanColumn {

            public NullableBooleanColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }

        public class NumberColumn<T extends Number> extends Column {

            protected String sql;

            public NumberColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final T value) {
                sql = columnName + "=" + value;
                return getWhereClause();
            }

            public WhereClause  gt(final T value) {
                sql = columnName + ">" + value;
                return getWhereClause();
            }

            public WhereClause  gte(final T value) {
                sql = columnName + ">=" + value;
                return getWhereClause();
            }

            public WhereClause  lt(final T value) {
                sql = columnName + "<" + value;
                return getWhereClause();
            }

            public WhereClause  lte(final T value) {
                sql = columnName + "<=" + value;
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableNumberColumn<T extends Number> extends NumberColumn<T> {

            public NullableNumberColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }

        public class DateColumn<LocalDate> extends Column {
        
            protected String sql;

            public DateColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final LocalDate value) {
                sql = columnName + "=" + value;
                return getWhereClause();
            }

            public WhereClause  gt(final LocalDate value) {
                sql = columnName + ">" + value;
                return getWhereClause();
            }

            public WhereClause  gte(final LocalDate value) {
                sql = columnName + ">=" + value;
                return getWhereClause();
            }

            public WhereClause  lt(final LocalDate value) {
                sql = columnName + "<" + value;
                return getWhereClause();
            }

            public WhereClause  lte(final LocalDate value) {
                sql = columnName + "<=" + value;
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableDateColumn extends DateColumn {

            public NullableDateColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }

        public class TimeColumn<LocalTime> extends Column {

            protected String sql;

            public TimeColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final LocalTime value) {
                sql = columnName + "=" + value;
                return getWhereClause();
            }

            public WhereClause  gt(final LocalTime value) {
                sql = columnName + ">" + value;
                return getWhereClause();
            }

            public WhereClause  gte(final LocalTime value) {
                sql = columnName + ">=" + value;
                return getWhereClause();
            }

            public WhereClause  lt(final LocalTime value) {
                sql = columnName + "<" + value;
                return getWhereClause();
            }

            public WhereClause  lte(final LocalTime value) {
                sql = columnName + "<=" + value;
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableTimeColumn extends TimeColumn {

            public NullableTimeColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }


        public class DateTimeColumn<LocalDateTime> extends Column {

            protected String sql;

            public DateTimeColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  eq(final LocalDateTime value) {
                sql = columnName + "=" + value;
                return getWhereClause();
            }

            public WhereClause  gt(final LocalDateTime value) {
                sql = columnName + ">" + value;
                return getWhereClause();
            }

            public WhereClause  gte(final LocalDateTime value) {
                sql = columnName + ">=" + value;
                return getWhereClause();
            }

            public WhereClause  lt(final LocalDateTime value) {
                sql = columnName + "<" + value;
                return getWhereClause();
            }

            public WhereClause  lte(final LocalDateTime value) {
                sql = columnName + "<=" + value;
                return getWhereClause();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableDateTimeColumn extends DateTimeColumn {

            public NullableDateTimeColumn(final String columnName, final PartialWhereClause  whereClause) {
                super(columnName, whereClause);
            }

            public WhereClause  isNull() {
                sql = columnName + " IS NULL";
                return getWhereClause();
            }

            public WhereClause  isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getWhereClause();
            }
        }

    }

}<#assign a=addImportStatement("java.util.ArrayList")><#assign a=addImportStatement("java.time.LocalDate")>
</#assign>
<@importStatements/>

${capturedOutput}