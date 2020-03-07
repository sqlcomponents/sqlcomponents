<#include "/template/dao/java/ibatis/ibatisbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}.</#if>sqlmapdao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import java.sql.SQLException ;
import ${daoPackage}.${name}Dao${orm.daoSuffix};

<#assign capturedOutput>
/**
 * 
 */
public class SqlMap${name}Dao${orm.daoSuffix} extends SqlMapClientDaoSupport implements ${name}Dao${orm.daoSuffix} {

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