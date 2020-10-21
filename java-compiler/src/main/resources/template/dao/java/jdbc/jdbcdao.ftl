<#include "/template/dao/java/jdbc/jdbcbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}.</#if>jdbc;

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
public class Jdbc${name}Repository${orm.daoSuffix}  {

  private final DataSource dataSource;

  public Jdbc${name}Repository${orm.daoSuffix}(DataSource dataSource) {
    this.dataSource = dataSource;
  }

	<#list orm.methodSpecification as method>
		<#include "method/${method}.ftl">				
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
				public StringQuery ${property.name}() {
					StringQuery query = new StringQuery("${property.columnName}",this);
					this.nodes.add(query);
					return query;
				}
				<#break>
			<#case "java.lang.Integer">
				public NumberQuery<Integer> ${property.name}() {
					NumberQuery<Integer> query = new NumberQuery("${property.columnName}",this);
					this.nodes.add(query);
					return query;
				}
				<#break>
			<#case "java.lang.Long">
				public NumberQuery<Long> ${property.name}() {
					NumberQuery<Long> query = new NumberQuery("${property.columnName}",this);
					this.nodes.add(query);
					return query;
				}
				<#break>
				<#case "java.lang.Float">
				public NumberQuery<Float> ${property.name}() {
					NumberQuery<Float> query = new NumberQuery("${property.columnName}",this);
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

        public String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                String asSql ;
                if(node instanceof Query) {
                    asSql = ((Query) node ).asSql();
                }else if (node instanceof Criteria) {
                    asSql = "(" + ((Criteria) node ).asSql() + ")";
                }
                else {
                    asSql = (String) node;
                }
                return asSql;
            }).collect(Collectors.joining(" "));
        }

        public abstract class Query {

            protected final String columnName;
            protected final Criteria criteria;

            public Query(final String columnName, final Criteria criteria) {
                this.columnName = columnName;
                this.criteria = criteria;
            }

            abstract String asSql();

        }

        public class StringQuery extends Query {
            private String sql ;

            public StringQuery(final String columnName, final Criteria criteria) {
                super(columnName,criteria);
            }

            public Criteria eq(final String l) {
                sql = columnName + "='" + l + "'";
                return criteria;
            }

            public Criteria like(final String l) {
                sql = columnName + " LIKE '" + l + "'";
                return criteria;
            }

            @Override
            String asSql() {
                return sql;
            }
        }

        public class NumberQuery<T extends Number> extends Query {

            private String sql ;

            public NumberQuery(final String columnName, final Criteria criteria) {
                super(columnName,criteria);
            }

            public Criteria eq(final T l) {
                sql = columnName + "=" + l;
                return criteria;
            }

            public Criteria gt(final T l) {
                sql = columnName + ">" + l;
                return criteria;
            }

            public Criteria gte(final T l) {
                sql = columnName + ">=" + l;
                return criteria;
            }

            public Criteria lt(final T l) {
                sql = columnName + "<" + l;
                return criteria;
            }

            public Criteria lte(final T l) {
                sql = columnName + "<=" + l;
                return criteria;
            }

            @Override
            String asSql() {
                return sql;
            }
        }
    }





}
</#assign>
<@importStatements/>

${capturedOutput}