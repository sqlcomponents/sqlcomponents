<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN"
"http://www.springframework.org/dtd/spring-beans.dtd">

    <beans>
    
	<#include "datasource.ftl">
	
    <bean id="${userName}SqlMapClient" 
    class="org.springframework.orm.ibatis.SqlMapClientFactoryBean">
        <property name="configLocation">
            <value>classpath:SqlMapConfig.xml</value>
        </property>
    </bean>

	<#list entities as entity>
	<bean id="${entity.name?uncap_first}Dao${daoSuffix}" class="${entity.daoPackage}.sqlmapdao.SqlMap${entity.name}Dao${daoSuffix}">
        <property name="dataSource"><ref bean="${name}DS"/></property>
        <property name="sqlMapClient"><ref local="${userName}SqlMapClient"/></property>
    </bean>
	</#list>
    
</beans>