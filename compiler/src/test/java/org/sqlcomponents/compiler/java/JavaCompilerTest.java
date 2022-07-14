package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.exception.SQLComponentsException;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

class JavaCompilerTest
{
    @Test
    void writeCode() throws Exception
    {
	Application application;
	if (System.getenv("SQLCOMPONENTS_CONFIG") == null)
	{
	    application = new Application();
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



	    application.setRootPackage("org.example");

	    application.setMethodSpecification(Application.METHOD_SPECIFICATION);
	    application.setSrcFolder("../datastore/src/main/java");
	}
	else
	{
	    application = CoreConsts.buildApplication(new File(System.getenv("SQLCOMPONENTS_CONFIG")));
	    if (System.getenv("SOURCE_FOLDER") == null)
	    {
		throw new IllegalArgumentException("SOURCE_FOLDER is not set");
	    }
	    application.setSrcFolder(System.getenv("SOURCE_FOLDER"));
	}
	application.compile(new JavaFTLCompiler());
	System.out.println("Code is compiled into " + application.getSrcFolder());
    }
}