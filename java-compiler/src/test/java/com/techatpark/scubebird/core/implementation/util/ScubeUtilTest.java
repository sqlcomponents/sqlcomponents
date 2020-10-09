package com.techatpark.scubebird.core.implementation.util;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.implementation.mapper.Mapper;
import com.techatpark.scubebird.core.implementation.mapper.java.JavaOrmMapper;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.crawler.oracle.OracleCrawler;
import com.techatpark.scubebird.crawler.postgres.PostgresCrawler;
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

        private final DaoProject daoProject ;

        Application() {
            this.daoProject = new DaoProject();;
        }

        Application connectToPostgress() throws SQLException, ScubeException {
            daoProject.setName("Movie");
            daoProject.setUrl("jdbc:postgresql://localhost:5432/sakila");
            daoProject.setUserName("postgres");
            daoProject.setPassword("postgres");
            daoProject.setSchemaName("sakila");
            daoProject.setTablePatterns(Arrays.asList("%"));

            daoProject.setOnline(true);
            daoProject.setBeanIdentifier("model");
            daoProject.setDaoIdentifier("dao");
            daoProject.setDaoSuffix("");
            daoProject.setRootPackage("org.example");
            daoProject.setCleanSource(true);


            Crawler crawler = new PostgresCrawler(daoProject);
            Mapper mapper = new JavaOrmMapper();
            daoProject.setOrm(mapper.getOrm(daoProject,crawler));
            return this;
        }

        Application connectToOracle() throws SQLException, ScubeException {
            daoProject.setName("Movie");
            daoProject.setUrl("jdbc:oracle:thin:@localhost:51521:xe");
            daoProject.setUserName("c##movie");
            daoProject.setPassword("movie");
            daoProject.setSchemaName("c##movie");
            daoProject.setTablePatterns(Arrays.asList("%"));
            return this;
        }

        Application understand() {

            daoProject.setMethodSpecification(Arrays.asList("DeleteByEntity", "DeleteByPK", "Without", "GetAll",
                    "GetByEntity", "GetByPK", "GetByPKExceptHighest", "GetByPKUniqueKeys",
                    "InsertByEntiy", "IsExisting", "MViewRefresh", "SaveByPK", "UpdateByEntiy", "UpdateByPK"));
            return this;
        }

        Application mapToJava() {

            return this;
        }

        void writeCode() throws SQLException, ScubeException {
            daoProject.setSrcFolder("/home/haripriya/Official/java-oracle-jdbc/target/generated-sources/sakila-postgres");

            ScubeUtil.writeCode(daoProject);

            System.out.println("Granted !");
        }


    }
}