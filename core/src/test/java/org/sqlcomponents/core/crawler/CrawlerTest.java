package org.sqlcomponents.core.crawler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class CrawlerTest {
    @Test
    void getDatabase() throws Exception {
        Application application;

        if (System.getenv("SQLCOMPONENTS_CONFIG") == null) {
            Properties props = new Properties();


            File dbPropertiesFile = new File("../database.properties");

            if (!dbPropertiesFile.exists()) {
                dbPropertiesFile = new File("database.properties");
            }


            props.load(new FileReader(dbPropertiesFile));

            String databaseType =
                    System.getenv("DATABASE_TYPE") == null ? "postgres" :
                            System.getenv("DATABASE_TYPE");

            application = new Application();
            application.setName("Movie");
            application.setUrl(
                    props.getProperty(databaseType + ".datasource.url"));
            application.setUserName(
                    props.getProperty(databaseType + ".datasource.username"));
            application.setPassword(
                    props.getProperty(databaseType + ".datasource.password"));
            application.setSchemaName(
                    props.getProperty(databaseType + ".datasource.schema"));

            application.setTablePatterns(List.of("^(?!pg_).*","^(?!_pg_).*"));

        } else {
            application = CoreConsts.buildApplication(
                    new File(System.getenv("SQLCOMPONENTS_CONFIG")));
        }

        // List<String> tablePatterns = new ArrayList<>();
        // tablePatterns.add("kafk\\w+");
        // application.setTablePatterns(tablePatterns);

        Database database = Crawler.getDatabase(application);
        Assertions.assertNotNull(application, "Database is not crawled");
    }
}