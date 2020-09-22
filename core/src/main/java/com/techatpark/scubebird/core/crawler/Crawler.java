package com.techatpark.scubebird.core.crawler;

import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Crawler {

	public static Crawler getCrawler(DaoProject project) throws ScubeException {
		Object crawlerInstance = null;
		try {
			Class<? extends Object> crawlerClass = Class.forName(project.getCrawlerConfig()
					.getClassName());

			crawlerInstance = crawlerClass.newInstance();
			if (!(crawlerInstance instanceof Crawler)) {
				throw new ScubeException(
						"crawlerClass "
								+ project.getCrawlerConfig().getClassName()
								+ " has to be subclass of  com.techatpark.scubebird.core.crawler.Crawler");
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ScubeException("crawlerClass "
					+ project.getCrawlerConfig().getClassName()
					+ " can not be accessed", e);
		}

		return (Crawler) crawlerInstance;
	}

	protected Connection getConnection(DaoProject project) throws SQLException,
			ClassNotFoundException {
		Class.forName(project.getCrawlerConfig().getDriverName());
		return DriverManager.getConnection(project.getUrl(), project
				.getUserName(), project.getPassword());
	}

	public abstract Schema getSchema(DaoProject ormProject)
			throws ScubeException;

}
