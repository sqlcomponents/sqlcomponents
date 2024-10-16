package org.sqlcomponents.core.crawler.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sqlcomponents.core.model.Application;

import javax.sql.DataSource;
import java.util.IdentityHashMap;
import java.util.Map;

public final class DataSourceUtil {

    /**
     * Caches Data Sources.
     */
    private static Map<Application, DataSource> dsMap = new IdentityHashMap<>();

    private DataSourceUtil() {
    }

    /**
     * This is the method for creating a datasource.
     *
     * @param application
     * @return DataSource
     */
    public static DataSource getDataSource(final Application application) {
        DataSource dataSource = dsMap.get(application);
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(application.getUrl());
            config.setUsername(application.getUserName());
            config.setPassword(application.getPassword());
            if (application.getUrl().contains(":postgresql:")) {
                config.setDriverClassName("org.postgresql.Driver");
            } else if (application.getUrl().contains(":h2:")) {
                config.setDriverClassName("org.h2.Driver");
            } else if (application.getUrl().contains(":oracle:")) {
                config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            }
            dataSource = new HikariDataSource(config);
            dsMap.put(application, dataSource);
        }


        return dataSource;
    }
}
