package org.sqlcomponents.core.crawler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


class CrawlerTest {

    @Test
    void getDatabase() throws SQLException, ScubeException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("../database.properties"));
        } catch (IOException e) {
            // Unreachable
            e.printStackTrace();
        }
        String databaseType = "postgres";
        Application application = new Application();
        application.setName("Movie");
        application.setUrl(props.getProperty(databaseType+".datasource.url"));
        application.setUserName(props.getProperty(databaseType+".datasource.username"));
        application.setPassword(props.getProperty(databaseType+".datasource.password"));
        application.setSchemaName(props.getProperty(databaseType+".datasource.schema"));

        Database database = new Crawler().getDatabase(application);

        Assertions.assertNotNull(application,"Database is not crawled");
    }
}