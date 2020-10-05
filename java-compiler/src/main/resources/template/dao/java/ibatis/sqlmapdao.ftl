<#include "/template/dao/java/ibatis/ibatisbase.ftl">
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}.</#if>sqlmapdao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import java.sql.SQLException ;
import ${daoPackage}.${name}Dao${orm.daoSuffix};

<#assign capturedOutput>
/**
 * 
 */
 @Component
public class SqlMap${name}Dao${orm.daoSuffix} implements ${name}Dao${orm.daoSuffix} {

    private final SqlSession sqlSession;

  public SqlMap${name}Dao${orm.daoSuffix}(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
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