package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.mapper.JavaMapper;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.mapper.Mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

class JavaCompilerTest {
    @Test
    void writeCode() throws ScubeException, SQLException, IOException {
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




            return this;
        }

        Application understand() {

            application.setMethodSpecification(Arrays.asList(
                    "InsertByEntiy",
                    "DeleteByPK",
                    "DeleteByEntity",
                    "DeleteAll",
                    "GetAll",
                    "GetByEntity",
                    "GetByPKExceptHighest",
                    "GetByPKUniqueKeys",
                    "IsExisting",
                    "MViewRefresh",
                    "UpdateByPK",
                    "GetByPK"
            ));
            return this;
        }

        Application mapToJava() {
            return this;
        }

        void writeCode() throws IOException, ScubeException {
            application.setSrcFolder("../java-test/target/generated-sources/movie-db");
            application.compile(new JavaCompiler());
            System.out.println("Code is compiled into "+ application.getSrcFolder());
        }

    }
}