package com.techatpark.scubebird.core.crawler;

import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Crawler {

	protected final DaoProject ormProject;

	protected Crawler(final DaoProject ormProject) throws SQLException {
		this.ormProject = ormProject;
		Connection connection = getConnection();
		DatabaseMetaData databasemetadata = connection.getMetaData();
		ormProject.setDriverName(databasemetadata.getDriverName());
	}

	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(ormProject.getUrl(), ormProject
				.getUserName(), ormProject.getPassword());
	}

	public abstract Schema getSchema()
			throws ScubeException;

}
