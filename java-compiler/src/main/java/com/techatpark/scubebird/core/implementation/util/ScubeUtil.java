package com.techatpark.scubebird.core.implementation.util;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.implementation.OrmImplementation;
import com.techatpark.scubebird.core.implementation.mapper.Mapper;
import com.techatpark.scubebird.core.implementation.mapper.java.JavaOrmMapper;
import com.techatpark.scubebird.core.implementation.specification.Specification;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.crawler.postgres.PostgresCrawler;

public class ScubeUtil {


	public static void writeCode(DaoProject daoProject) throws ScubeException {
		Crawler crawler = new PostgresCrawler(daoProject);
		Mapper mapper = new JavaOrmMapper();
		daoProject.setOrm(mapper.getOrm(daoProject,crawler));
		if (daoProject.isCleanSource()) {
			FileUtil.deleteDir(daoProject.getSrcFolder());
		}
		Specification.getSpecification().writeSpecification(daoProject);
		OrmImplementation.getImplementation().writeImplementation(daoProject);
	}
}
