package com.techatpark.scubebird.crawler.postgres;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.CrawlerConfig;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;
import org.junit.jupiter.api.Test;

import java.util.List;

class PostgresCrawlerTest {

    @Test
    void testGetSchema() throws ScubeException {
        DaoProject daoProject = new DaoProject();

        daoProject.setName("Sakila");
        daoProject.setUrl("jdbc:postgresql://localhost:5432/sakila");
        daoProject.setUserName("postgres");
        daoProject.setPassword("postgres");
        daoProject.setSchemaName("sakila");
        daoProject.setTablePatterns(List.of("%"));

        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setClassName("com.techatpark.scubebird.crawler.postgres.PostgresCrawler");
        crawlerConfig.setDriverName("org.postgresql.Driver");
        crawlerConfig.setName("Postgres");
        daoProject.setCrawlerConfig(crawlerConfig);

        Crawler crawler = new PostgresCrawler();
        Schema schema = crawler.getSchema(daoProject);
        System.out.printf("schema");
    }
}