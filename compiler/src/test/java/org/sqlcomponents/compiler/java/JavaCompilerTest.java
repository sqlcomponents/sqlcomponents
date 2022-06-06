package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

class JavaCompilerTest {
    @Test
    void writeCode() throws ScubeException, IOException {

		final String srcFolder = System.getenv("SOURCE_FOLDER") == null
				? "../datastore/src/main/java" : System.getenv("SOURCE_FOLDER");

		final Application application = new Application();

		Properties props = new Properties();
		props.load(new FileReader("../database.properties"));

		String databaseType = System.getenv("DATABASE_TYPE") == null
				? "postgres" : System.getenv("DATABASE_TYPE");

		application.setName("Movie");
		application.setUrl(props.getProperty(databaseType + ".datasource.url"));
		application.setUserName(props.getProperty(databaseType + ".datasource.username"));
		application.setPassword(props.getProperty(databaseType + ".datasource.password"));
		application.setSchemaName(props.getProperty(databaseType + ".datasource.schema"));
		// daoProject.setTablePatterns(Arrays.asList("movie"));

		application.setOnline(true);
		application.setBeanIdentifier("model");
		application.setDaoIdentifier("store");
		application.setDaoSuffix("");
		application.setRootPackage("org.example");
		application.setCleanSource(true);

		application.setMethodSpecification(Application.METHOD_SPECIFICATION);

		application.setSrcFolder(srcFolder);
		application.compile(new JavaFTLCompiler());
		System.out.println("Code is compiled into " + application.getSrcFolder());
    }

}