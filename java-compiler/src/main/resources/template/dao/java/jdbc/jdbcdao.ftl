<#include "/template/dao/java/jdbc/jdbcbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}</#if>;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

<#assign capturedOutput>
/**
 * 
 */
public class ${name}Store${orm.daoSuffix}  {

  private final DataSource dataSource;

  public ${name}Store${orm.daoSuffix}(DataSource dataSource) {
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
        final ${name} obj = new ${name}();
		<#list properties as property>
		obj.set${property.name?cap_first}(rs.get${getJDBCClassName(property.dataType)}("${property.columnName}"));
		</#list>
        return obj;
    }

    public static Criteria where() {
        return new Criteria();
    }

    public static class Criteria {

        private final List<Object> nodes;

        public Criteria() {
            this.nodes = new ArrayList<>();
        }
<#list properties as property>

    <#switch property.dataType>
    <#case "java.lang.String">
        public <#if property.column.isNullable??>Nullable</#if>StringField ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>StringField query = new <#if property.column.isNullable??>Nullable</#if>StringField("${property.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Integer">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Integer> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Integer> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Long">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Long> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Long> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    <#case "java.lang.Float">
        public <#if property.column.isNullable??>Nullable</#if>NumberField<Float> ${property.name}() {
            <#if property.column.isNullable??>Nullable</#if>NumberField<Float> query = new <#if property.column.isNullable??>Nullable</#if>NumberField("${property.columnName}",this);
            this.nodes.add(query);
            return query;
        }
        <#break>
    </#switch>


		</#list>

        public Criteria and() {
            this.nodes.add("AND");
            return this;
        }

        public Criteria or() {
            this.nodes.add("OR");
            return this;
        }

        public Criteria and(final Criteria criteria) {
            this.nodes.add("AND");
            this.nodes.add(criteria);
            return this;
        }

        public Criteria or(final Criteria criteria) {
            this.nodes.add("OR");
            this.nodes.add(criteria);
            return this;
        }

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

        public abstract class Field {

            protected final String columnName;
            protected final Criteria criteria;

            public Field(final String columnName, final Criteria criteria) {
                this.columnName = columnName;
                this.criteria = criteria;
            }

            protected abstract String asSql();

        }

        public class StringField extends Field {
            protected String sql;

            public StringField(final String columnName, final Criteria criteria) {
                super(columnName, criteria);
            }

            public Criteria eq(final String value) {
                sql = columnName + "='" + value + "'";
                return criteria;
            }

            public Criteria like(final String value) {
                sql = columnName + " LIKE '" + value + "'";
                return criteria;
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }

        public class NullableStringField extends StringField {


            public NullableStringField(final String columnName, final Criteria criteria) {
                super(columnName, criteria);
            }

            public Criteria isNull() {
                sql = columnName + " IS NULL";
                return criteria;
            }

            public Criteria isNotNull() {
                sql = columnName + " IS NOT NULL";
                return criteria;
            }
        }

        public class NumberField<T extends Number> extends Field {

            protected String sql;

            public NumberField(final String columnName, final Criteria criteria) {
                super(columnName, criteria);
            }

            public Criteria eq(final T value) {
                sql = columnName + "=" + value;
                return criteria;
            }

            public Criteria gt(final T value) {
                sql = columnName + ">" + value;
                return criteria;
            }

            public Criteria gte(final T value) {
                sql = columnName + ">=" + value;
                return criteria;
            }

            public Criteria lt(final T value) {
                sql = columnName + "<" + value;
                return criteria;
            }

            public Criteria lte(final T value) {
                sql = columnName + "<=" + value;
                return criteria;
            }

            @Override
            protected String asSql() {
                return sql;
            }
        }



        public class NullableNumberField<T extends Number> extends NumberField<T> {


            public NullableNumberField(final String columnName, final Criteria criteria) {
                super(columnName, criteria);
            }

            public Criteria isNull() {
                sql = columnName + " IS NULL";
                return criteria;
            }

            public Criteria isNotNull() {
                sql = columnName + " IS NOT NULL";
                return criteria;
            }
        }
    }






}
</#assign>
<@importStatements/>

${capturedOutput}