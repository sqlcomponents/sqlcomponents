package org.example.util;

import org.mariadb.jdbc.MySQLDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
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
        String databaseType = System.getenv("DATABASE_TYPE") == null
                ? "postgres" : System.getenv("DATABASE_TYPE");
        if(databaseType.equals("postgres")) {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL(props.getProperty(databaseType+".datasource.url"));
            ds.setUser(props.getProperty(databaseType+".datasource.username"));
            ds.setPassword(props.getProperty(databaseType+".datasource.password"));
            return ds;
        }
        if(databaseType.equals("mysql")) {
            MySQLDataSource mySQLDataSource = new MySQLDataSource();
            try {
                mySQLDataSource.setUrl(props.getProperty(databaseType+".datasource.url"));
                mySQLDataSource.setUser(props.getProperty(databaseType+".datasource.username"));
                mySQLDataSource.setPassword(props.getProperty(databaseType+".datasource.password"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
             return mySQLDataSource;
        }
        return null;
    }
}
