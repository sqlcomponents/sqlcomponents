package org.sqlcomponents.compiler.util;

import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.compiler.mapper.Mapper;
import org.sqlcomponents.compiler.mapper.java.JavaOrmMapper;
import org.sqlcomponents.core.model.DaoProject;
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
            daoProject.setUrl("jdbc:postgresql://localhost:5432/moviedb");
            daoProject.setUserName("moviedb");
            daoProject.setPassword("moviedb");
            daoProject.setSchemaName("moviedb");
            // daoProject.setTablePatterns(Arrays.asList("movie"));

            daoProject.setOnline(true);
            daoProject.setBeanIdentifier("model");
            daoProject.setDaoIdentifier("store");
            daoProject.setDaoSuffix("");
            daoProject.setRootPackage("org.example");
            daoProject.setCleanSource(true);


            Crawler crawler = new Crawler(daoProject);
            Mapper mapper = new JavaOrmMapper();
            daoProject.setOrm(mapper.getOrm(daoProject,crawler));
            return this;
        }

        Application understand() {

            daoProject.setMethodSpecification(Arrays.asList(
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
            daoProject.setSrcFolder(System.getenv("JDBC_CODE_FOLDER"));
            ScubeUtil.writeCode(daoProject);
            System.out.println("Granted !");
        }

    }
}