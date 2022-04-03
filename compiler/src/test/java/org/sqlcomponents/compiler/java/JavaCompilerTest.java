package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

class JavaCompilerTest
{
    @Test
    void writeCode() throws ScubeException, SQLException, IOException
    {
	new TestingTimeApplication().connectToPostgres().understand().mapToJava().writeCode();
    }

    static class TestingTimeApplication
    {
	private final org.sqlcomponents.core.model.Application application;

	TestingTimeApplication()
	{
	    this.application = new org.sqlcomponents.core.model.Application();
	}

	TestingTimeApplication connectToPostgres() throws SQLException, ScubeException
	{
	    Properties props = new Properties();
	    try
	    {
		props.load(new FileReader("../database.properties"));
	    }
	    catch (IOException e)
	    {
		// Unreachable
		e.printStackTrace();
	    }
	    String databaseType = System.getenv("DATABASE_TYPE") == null ? "postgres" : System.getenv("DATABASE_TYPE");

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
	    return this;
	}

	TestingTimeApplication understand()
	{
	    application.setMethodSpecification(Application.METHOD_SPECIFICATION);
	    return this;
	}

	TestingTimeApplication mapToJava()
	{
	    return this;
	}

	void writeCode() throws IOException, ScubeException
	{
	    application.setSrcFolder("../datastore/src/main/java");
	    application.compile(new JavaFTLCompiler());
	    System.out.println("Code is compiled into " + application.getSrcFolder());
	}
    }
}