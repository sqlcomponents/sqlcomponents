package org.example.util;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public final class DataSourceProvider {
    public static DataSource dataSource()  {
        Properties props = new Properties();
        try {
            props.load(DataSourceProvider.class.getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            // Unreachable
            e.printStackTrace();
        }
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(props.getProperty("datasource.url"));
        ds.setUser(props.getProperty("datasource.username"));
        ds.setPassword(props.getProperty("datasource.password"));
        return ds;
    }
}
