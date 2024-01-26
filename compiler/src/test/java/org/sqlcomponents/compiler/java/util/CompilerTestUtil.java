package org.sqlcomponents.compiler.java.util;

import org.sqlcomponents.compiler.java.mapper.JavaMapper;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CompilerTestUtil {

    private static Application application;;
    private static Database database;
    private static JavaMapper javaMapper;

    public static Application getApplication() throws IOException {
        if (application == null) {
            if ( System.getenv("SQLCOMPONENTS_CONFIG") == null) {
                application = new Application();
                Properties props = new Properties();
                File dbPropertiesFile = new File("../database.properties");

                if (!dbPropertiesFile.exists()) {
                    dbPropertiesFile = new File("database.properties");
                }


                props.load(new FileReader(dbPropertiesFile));

                String databaseType =
                        System.getenv("DATABASE_TYPE") == null ? "postgres" :
                                System.getenv("DATABASE_TYPE");

                application.setName("Raja");
                application.setUrl(
                        props.getProperty(databaseType + ".datasource.url"));
                application.setUserName(
                        props.getProperty(databaseType + ".datasource.username"));
                application.setPassword(
                        props.getProperty(databaseType + ".datasource.password"));
                application.setSchemaName(
                        props.getProperty(databaseType + ".datasource.schema"));
                // daoProject.setTablePatterns(Arrays.asList("movie"));

                Map<String, String> insertMap = new HashMap<>();
                insertMap.put("created_at", "CURRENT_TIMESTAMP");
                insertMap.put("modified_by", null);
                insertMap.put("modified_at", null);
                insertMap.put("raja#a_integer", "4");
                application.setInsertMap(insertMap);

                Map<String, String> updateMap = new HashMap<>();
                updateMap.put("modified_at", "CURRENT_TIMESTAMP");
                updateMap.put("created_by", null);
                updateMap.put("created_at", null);
                updateMap.put("raja#a_integer", "5");
                application.setUpdateMap(updateMap);

                application.setEncryption(Arrays.asList("a_encrypted_text"));

                application.setRootPackage("org.example");

                application.setMethodSpecification(
                        Application.METHOD_SPECIFICATION);
                File file ;
                if (!new File("datastore/src").exists()) {
                    file = new File("../datastore/src/main/java");
                }
                else {
                    file = new File("datastore/src/main/java");
                }
                application.setSrcFolder(file.getAbsolutePath());

            } else {
                application = CoreConsts.buildApplication(
                        new File(System.getenv("SQLCOMPONENTS_CONFIG")));
                if (System.getenv("SOURCE_FOLDER") == null) {
                    throw new IllegalArgumentException("SOURCE_FOLDER is not set");
                }
                application.setSrcFolder(System.getenv("SOURCE_FOLDER"));
            }
        }

        return application;
    }

    public static  String getDataType(final String columnName) throws SQLException, IOException {
        if(javaMapper == null) {
            database = new Crawler(getApplication()).getDatabase();
            javaMapper = new JavaMapper(getApplication());
        }
        return javaMapper.getDataType(database.getTables().stream()
                .filter(table -> table.getTableName().equals("raja")).findFirst()
                .get().getColumns().stream()
                .filter(column -> column.getColumnName().equals(columnName)).findFirst().get());
    }
}
