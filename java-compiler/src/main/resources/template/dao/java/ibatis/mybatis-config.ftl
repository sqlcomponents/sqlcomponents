<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
    <#list entities as entity>
        <typeAlias alias="${entity.name}" type="${entity.beanPackage}.${entity.name}"/>
    </#list>
    </typeAliases>
    <mappers>
    <#list entities as entity>
        <mapper resource="${entity.daoPackage?replace(".", "/")}/sqlmapdao/sqlmap/${entity.name}.xml"/>
    </#list>
    </mappers>
</configuration>