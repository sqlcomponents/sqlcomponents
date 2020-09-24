package com.techatpark.scubebird.crawler.oracle;

import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.CrawlerConfig;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;
import org.junit.jupiter.api.Test;

import java.util.List;

class OracleCrawlerTest {

    @Test
    void testGetSchema() throws ScubeException {
        DaoProject daoProject = new DaoProject();

        daoProject.setName("Sakila");
        daoProject.setUrl("jdbc:oracle:thin:@localhost:51521:xe");
        daoProject.setUserName("system");
        daoProject.setPassword("mysecurepassword");
        daoProject.setSchemaName("APPQOSSYS");
        daoProject.setTablePatterns(List.of("%"));

        CrawlerConfig crawlerConfig = new CrawlerConfig();
        crawlerConfig.setClassName("com.techatpark.scubebird.crawler.oracle.OracleCrawler");
        crawlerConfig.setDriverName("oracle.jdbc.driver.OracleDriver");
        crawlerConfig.setName("Oracle");
        daoProject.setCrawlerConfig(crawlerConfig);

        OracleCrawler oracleCrawler = new OracleCrawler();
        Schema schema = oracleCrawler.getSchema(daoProject);
        System.out.printf("schema");
    }
}