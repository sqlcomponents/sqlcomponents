<#include "/template/dao/java/jdbc/jdbcbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}.</#if>jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException ;

import java.util.HashMap;
import java.util.List;

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
		obj.set${property.name?cap_first}(rs.get${getClassName(property.dataType)}("${property.columnName}"));
		</#list>
        return obj;
    }
}
</#assign>
<@importStatements/>

${capturedOutput}