package org.sqlcomponents.compiler.util;

import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.mapper.java.JavaOrmMapper;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

class ScubeUtilTest {

    @Test
    void writeCode() throws ScubeException, SQLException {
        new Application()
                .connectToPostgress()
                .understand()
                .mapToJava()
                .writeCode();
    }

    class Application {

        private final org.sqlcomponents.core.model.Application application;

        Application() {
            this.application = new org.sqlcomponents.core.model.Application();
        }

        Application connectToPostgress() throws SQLException, ScubeException {
            application.setName("Movie");
            application.setUrl("jdbc:postgresql://localhost:5432/moviedb");
            application.setUserName("moviedb");
            application.setPassword("moviedb");
            application.setSchemaName("moviedb");
            // daoProject.setTablePatterns(Arrays.asList("movie"));

            application.setOnline(true);
            application.setBeanIdentifier("model");
            application.setDaoIdentifier("store");
            application.setDaoSuffix("");
            application.setRootPackage("org.example");
            application.setCleanSource(true);


            Crawler crawler = new Crawler(application);
            Mapper mapper = new JavaOrmMapper();
            application.setOrm(mapper.getOrm(application,crawler));
            return this;
        }

        Application understand() {

            application.setMethodSpecification(Arrays.asList(
                    "DeleteByEntity"
                    , "DeleteByPK"
                    , "GetAll"
                    , "GetByEntity"
                    , "GetByPK"
                    , "GetByPKExceptHighest"
                    , "GetByPKUniqueKeys"
                    , "InsertByEntiy"
                    , "IsExisting"
                    , "MViewRefresh"
                    , "UpdateByPK"
            ));
            return this;
        }

        Application mapToJava() {
            return this;
        }

        void writeCode() throws SQLException, ScubeException {
            String sourceCodeFolder = System.getenv("JDBC_CODE_FOLDER");
            if(sourceCodeFolder == null) {
                throw new IllegalArgumentException("Set Environment Variable JDBC_CODE_FOLDER");
            }
            application.setSrcFolder(sourceCodeFolder);
            ScubeUtil.writeCode(application);
            System.out.println("Granted !");
        }

    }
}