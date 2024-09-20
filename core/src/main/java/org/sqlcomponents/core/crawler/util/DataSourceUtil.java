package org.sqlcomponents.core.crawler.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.sqlcomponents.core.model.Application;

import javax.sql.DataSource;

public final class DataSourceUtil {
    private DataSourceUtil() {
    }

    /**
     * This is the method for creating a datasource.
     *
     * @param application
     * @return DataSource
     */
    public static DataSource getDataSource(final Application application) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(application.getUrl());
        config.setUsername(application.getUserName());
        config.setPassword(application.getPassword());
//        config.setSchema(application.getSchemaName());
        if (application.getUrl().contains(":postgresql:")) {
            config.setDriverClassName("org.postgresql.Driver");
        } else if (application.getUrl().contains(":h2:")) {
            config.setDriverClassName("org.h2.Driver");
        } else if (application.getUrl().contains(":oracle:")) {
            config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        return new HikariDataSource(config);
    }
}
