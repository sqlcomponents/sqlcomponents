package com.techatpark.scubebird.core.crawler;

import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Crawler {

	protected Connection getConnection(DaoProject project) throws SQLException,
			ClassNotFoundException {
		return DriverManager.getConnection(project.getUrl(), project
				.getUserName(), project.getPassword());
	}

	public abstract Schema getSchema(DaoProject ormProject)
			throws ScubeException;

}
