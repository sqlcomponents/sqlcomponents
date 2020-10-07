package com.techatpark.scubebird.core.implementation.util;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.mapper.Mapper;
import com.techatpark.scubebird.core.model.CrawlerConfig;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;
import com.techatpark.scubebird.crawler.postgres.PostgresCrawler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ScubeUtilTest {

    @Test
    void writeCode() throws IOException, ClassNotFoundException, ScubeException {
        DaoProject daoProject = new DaoProject();

        daoProject.setName("Sakila");
        daoProject.setUrl("jdbc:postgresql://localhost:5432/sakila");
        daoProject.setUserName("postgres");
        daoProject.setPassword("postgres");
        daoProject.setSchemaName("sakila");
        daoProject.setTablePatterns(List.of("actor"));

        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setClassName("com.techatpark.scubebird.crawler.postgres.PostgresCrawler");
        crawlerConfig.setDriverName("org.postgresql.Driver");
        crawlerConfig.setName("Postgres");
        daoProject.setCrawlerConfig(crawlerConfig);


        daoProject.setOnline(true);
        daoProject.setBeanIdentifier("model");
        daoProject.setDaoIdentifier("dao");

        daoProject.setDaoSuffix("");

        daoProject.setMethodSpecification(List.of("GetAll"));

        daoProject.setRootPackage("org.example");

        Crawler crawler = new PostgresCrawler();
        Schema schema = crawler.getSchema(daoProject);
        daoProject.setOrm(Mapper.getMapper(daoProject).getOrm(
                daoProject));
        daoProject.setSrcFolder("/Users/sathishkumarthiyagarajan/IdeaProjects/jdbc-java/target/generated-sources/sakila-postgres");
        daoProject.setCleanSource(true);
        ScubeUtil.writeCode(daoProject);
    }
}