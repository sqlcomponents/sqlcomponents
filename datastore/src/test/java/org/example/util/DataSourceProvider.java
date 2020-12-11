package org.example.util;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class DataSourceProvider {
    public static DataSource dataSource()  {
        Properties props = new Properties();
        try {
            props.load(new FileReader("../database.properties"));
        } catch (IOException e) {
            // Unreachable
            e.printStackTrace();
        }
        String databaseType = "postgres";
        if(databaseType.equals("postgres")) {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL(props.getProperty(databaseType+".datasource.url"));
            ds.setUser(props.getProperty(databaseType+".datasource.username"));
            ds.setPassword(props.getProperty(databaseType+".datasource.password"));
            return ds;
        }
        return null;
    }
}
