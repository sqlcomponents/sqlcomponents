<#include "/template/dao/java/jdbc/jdbcbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}</#if>;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

<#assign capturedOutput>
/**
 * 
 */
public final class ${name}Store${orm.daoSuffix}  {

    private final DataSource dataSource;

    public ${name}Store${orm.daoSuffix}(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

	<#list orm.methodSpecification as method>
		<#include "/template/dao/java/jdbc/method/${method}.ftl">
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
		${name?uncap_first}.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}(${index}));<#assign index = index + 1>
		</#list>
        return ${name?uncap_first};
    }

    public final InsertBuilder insert() {
        return new InsertBuilder(this);
    }

    public static class InsertBuilder {
        private final ${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix};

        private ${name} ${name?uncap_first};

        private InsertBuilder(${name}Store${orm.daoSuffix} ${name?uncap_first}Store${orm.daoSuffix}) {
            this.${name?uncap_first}Store${orm.daoSuffix} = ${name?uncap_first}Store${orm.daoSuffix};
        }

        public InsertBuilder value(${name} ${name?uncap_first}) {
            this.${name?uncap_first} = ${name?uncap_first};
            return this;
        }

        public void returning()  {

        }
    }

<#list properties as property>
    <#switch property.dataType>
    <#case "java.lang.String">
    public static PartialCriteria.<#if property.column.isNullable??>Nullable</#if>StringField ${property.name}() {
    <#break>
    <#case "java.lang.Integer">
    public static PartialCriteria.<#if property.column.isNullable??>Nullable</#if>NumberField<Integer> ${property.name}() {
    <#break>
    <#case "java.lang.Long">
    public static PartialCriteria.<#if property.column.isNullable??>Nullable</#if>NumberField<Long> ${property.name}() {
    <#break>
    <#case "java.lang.Float">
    public static PartialCriteria.<#if property.column.isNullable??>Nullable</#if>NumberField<Float> ${property.name}() {
    <#break>
    <#case "java.util.Date">
    public static PartialCriteria.<#if property.column.isNullable??>Nullable</#if>DateField ${property.name}() {
    <#break>
    </#switch>
        return new Criteria().${property.name}();
    }
    </#list>

    public static class Criteria extends PartialCriteria {
        private String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                String asSql;
                if (node instanceof Field) {
                    asSql = ((Field) node).asSql();
                } else if (node instanceof Criteria) {
                    asSql = "(" + ((Criteria) node).asSql() + ")";
                } else {
                    asSql = (String) node;
                }
                return asSql;
            }).collect(Collectors.joining(" "));
        }

        public PartialCriteria and() {
            this.nodes.add("AND");
            return this;
        }

        public PartialCriteria or() {
            this.nodes.add("OR");
            return this;
        }

        public Criteria and(final Criteria criteria) {
            this.nodes.add("AND");
            this.nodes.add(criteria);
            return (Criteria) this;
        }

        public Criteria or(final Criteria criteria) {
            this.nodes.add("OR");
            this.nodes.add(criteria);
            return (Criteria) this;
        }

    }

    public static class PartialCriteria {

        protected final List<Object> nodes;

        public PartialCriteria() {
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
    <#case "java.lang.Integer">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Integer> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Integer> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.column.columnName}",this);
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
    <#case "java.util.Date">
        public <#if property.column.isNullable??>Nullable</#if>DateField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>DateField query = new <#if property.column.isNullable??>Nullable</#if>DateField("${property.column.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    </#switch>
		</#list>

        public abstract class Field {

            protected final String columnName;
            private final PartialCriteria criteria;

            public Field(final String columnName, final PartialCriteria criteria) {
                this.columnName = columnName;
                this.criteria = criteria;
            }

            protected Criteria getCriteria() {
                return (Criteria) criteria;
            }

            protected abstract String asSql();

        }

        public class StringField extends Field {
            protected String sql;

            public StringField(final String columnName, final PartialCriteria criteria) {
                super(columnName, criteria);
            }

            public Criteria eq(final String value) {
                sql = columnName + "='" + value + "'";
                return getCriteria();
            }

            public Criteria like(final String value) {
                sql = columnName + " LIKE '" + value + "'";
                return getCriteria();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableStringField extends StringField {

            public NullableStringField(final String columnName, final PartialCriteria criteria) {
                super(columnName, criteria);
            }

            public Criteria isNull() {
                sql = columnName + " IS NULL";
                return getCriteria();
            }

            public Criteria isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getCriteria();
            }
        }

        public class NumberField<T extends Number> extends Field {

            protected String sql;

            public NumberField(final String columnName, final PartialCriteria criteria) {
                super(columnName, criteria);
            }

            public Criteria eq(final T value) {
                sql = columnName + "=" + value;
                return getCriteria();
            }

            public Criteria gt(final T value) {
                sql = columnName + ">" + value;
                return getCriteria();
            }

            public Criteria gte(final T value) {
                sql = columnName + ">=" + value;
                return getCriteria();
            }

            public Criteria lt(final T value) {
                sql = columnName + "<" + value;
                return getCriteria();
            }

            public Criteria lte(final T value) {
                sql = columnName + "<=" + value;
                return getCriteria();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableNumberField<T extends Number> extends NumberField<T> {

            public NullableNumberField(final String columnName, final PartialCriteria criteria) {
                super(columnName, criteria);
            }

            public Criteria isNull() {
                sql = columnName + " IS NULL";
                return getCriteria();
            }

            public Criteria isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getCriteria();
            }
        }

        public class DateField<Date> extends Field {
        
            protected String sql;

            public DateField(final String columnName, final PartialCriteria criteria) {
                super(columnName, criteria);
            }

            public Criteria eq(final Date value) {
                sql = columnName + "=" + value;
                return getCriteria();
            }

            public Criteria gt(final Date value) {
                sql = columnName + ">" + value;
                return getCriteria();
            }

            public Criteria gte(final Date value) {
                sql = columnName + ">=" + value;
                return getCriteria();
            }

            public Criteria lt(final Date value) {
                sql = columnName + "<" + value;
                return getCriteria();
            }

            public Criteria lte(final Date value) {
                sql = columnName + "<=" + value;
                return getCriteria();
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableDateField extends DateField {

            public NullableDateField(final String columnName, final PartialCriteria criteria) {
                super(columnName, criteria);
            }

            public Criteria isNull() {
                sql = columnName + " IS NULL";
                return getCriteria();
            }

            public Criteria isNotNull() {
                sql = columnName + " IS NOT NULL";
                return getCriteria();
            }
        }
    }

}<#assign a=addImportStatement("java.util.ArrayList")>
</#assign>
<@importStatements/>

${capturedOutput}