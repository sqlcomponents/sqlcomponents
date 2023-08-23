package org.example.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class DataSourceProvider {
    public static DataSource dataSource() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("../database.properties"));
        } catch (IOException e) {
            // Unreachable
            e.printStackTrace();
        }
        String databaseType =
                System.getenv("DATABASE_TYPE") == null ? "postgres" :
                        System.getenv("DATABASE_TYPE");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(props.getProperty(databaseType + ".datasource.url"));
        config.setUsername(
                props.getProperty(databaseType + ".datasource.username"));
        config.setPassword(
                props.getProperty(databaseType + ".datasource.password"));
        return new HikariDataSource(config);
    }
}
