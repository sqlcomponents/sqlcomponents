        <bean id="${name}DS" 
        class="org.apache.commons.dbcp.BasicDataSource" 
        destroy-method="close">
            <property name="driverClassName">
                <value>${crawlerConfig.driverName}</value>
            </property>
            <property name="url">
                <value>${url}</value></property>
            <property name="username"><value>${userName}</value></property>
            <property name="password"><value>${password}</value></property>
    </bean> 
