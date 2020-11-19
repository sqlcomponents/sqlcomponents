package org.example.util;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public final class DataSourceProvider {
    
    public static DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setUser("moviedb");
        ds.setPassword("moviedb");
        return ds;
    }
}
