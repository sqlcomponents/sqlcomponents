<#include "/template/dao/java/ibatis/ibatisbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}.</#if>sqlmapdao;

import javax.sql.DataSource;
import java.sql.SQLException ;
import ${daoPackage}.${name}Dao${orm.daoSuffix};

<#assign capturedOutput>
/**
 * 
 */
public class Jdbc${name}Dao${orm.daoSuffix} implements ${name}Dao${orm.daoSuffix} {

  private final DataSource dataSource;

  public Jdbc${name}Dao${orm.daoSuffix}(DataSource dataSource) {
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
}
</#assign>
<@importStatements/>

${capturedOutput}