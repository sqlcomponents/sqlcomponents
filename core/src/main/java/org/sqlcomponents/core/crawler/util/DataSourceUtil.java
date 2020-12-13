package org.sqlcomponents.core.crawler.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public final class DataSourceUtil {
    public static DataSource getDataSource(final String url, final String userName, final String password) {
        if(url.startsWith("jdbc:postgresql:")) {
            PGSimpleDataSource ds = new PGSimpleDataSource();
            ds.setURL(url);
            ds.setUser(userName);
            ds.setPassword(password);
            return ds;
        }
        if(url.startsWith("jdbc:mysql:")) {
            MysqlDataSource mySQLDataSource = new MysqlDataSource();
            mySQLDataSource.setUrl(url);
            mySQLDataSource.setUser(userName);
            mySQLDataSource.setPassword(password);
            return mySQLDataSource;
        }
        return null;
    }
}
