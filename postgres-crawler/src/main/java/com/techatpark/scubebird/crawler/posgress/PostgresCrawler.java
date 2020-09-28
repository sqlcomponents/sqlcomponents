package com.techatpark.scubebird.crawler.posgress;
import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;

public class PostgresCrawler extends Crawler {

    @Override
    public Schema getSchema(DaoProject ormProject) throws ScubeException {
        return null;
    }
}
