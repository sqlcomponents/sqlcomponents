<!-- JNDI DataSource in J2EE environments -->
    <!-- we need to copy database (and DBCP?) jar files to tomcat5.5/commons/lib. -->
    <bean id="${name}DS" class="org.springframework.jndi.JndiObjectFactoryBean">
        <property name="jndiName" value="java:/comp/env/jdbc/${name}DS"/>
    </bean>