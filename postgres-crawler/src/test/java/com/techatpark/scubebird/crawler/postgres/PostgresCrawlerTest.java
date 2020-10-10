package com.techatpark.scubebird.crawler.postgres;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

class PostgresCrawlerTest {

    @Test
    void testGetSchema() throws ScubeException, SQLException {
        DaoProject daoProject = new DaoProject();

        daoProject.setName("Sakila");
        daoProject.setUrl("jdbc:postgresql://localhost:5432/moviedb");
        daoProject.setUserName("moviedb");
        daoProject.setPassword("moviedb");
        daoProject.setSchemaName("moviedb");
        daoProject.setTablePatterns(Arrays.asList("%"));

        Crawler crawler = new PostgresCrawler(daoProject);
        Schema schema = crawler.getSchema();

        System.out.printf("schema");
    }
}