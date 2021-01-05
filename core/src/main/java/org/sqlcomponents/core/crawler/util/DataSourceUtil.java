package org.sqlcomponents.core.crawler.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public final class DataSourceUtil {
    private DataSourceUtil() {
    }

    /**
     * This is the method for creating a datasource.
     * @param url
     * @param userName
     * @param password
     * @return DataSource
     */
    public static DataSource getDataSource(final String url,
                                           final String userName,
                                           final String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);
        return new HikariDataSource(config);
    }
}
