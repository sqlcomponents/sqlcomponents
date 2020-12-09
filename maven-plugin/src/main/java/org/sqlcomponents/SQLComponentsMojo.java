package org.sqlcomponents;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.sqlcomponents.compiler.java.JavaCompiler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;

/**
 * Counts the number of maven dependencies of a project.
 *
 * It can be filtered by scope.
 *
 */
@Mojo(name = "dependency-counter", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class SQLComponentsMojo
    extends AbstractMojo
{
    /**
     * Scope to filter the dependencies.
     */
    @Parameter(property = "scope")
    String scope;

    /**
     * Gives access to the Maven project information.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @SneakyThrows
    public void execute()  {
        List<Dependency> dependencies = project.getDependencies();

        long numDependencies = dependencies.stream()
                .filter(d -> (scope == null || scope.isEmpty()) || scope.equals(d.getScope()))
                .count();

        getLog().info("Number of dependencies: " + numDependencies);

        Application application = getApplication();

        getLog().info("Code is compiled into " + application.getSrcFolder());

    }

    private Application getApplication() throws IOException, ScubeException {
        Application application = new Application();
        application.setName("Movie");
        application.setUrl("jdbc:postgresql://localhost:5432/moviedb");
        application.setUserName("moviedb");
        application.setPassword("moviedb");
        application.setSchemaName("moviedb");

        application.setOnline(true);
        application.setBeanIdentifier("model");
        application.setDaoIdentifier("store");
        application.setDaoSuffix("");
        application.setRootPackage("org.example");
        application.setCleanSource(true);

        application.setMethodSpecification(Arrays.asList(
                "InsertByEntiy",
                "DeleteByPK",
                "DeleteByEntity",
                "DeleteAll",
                "GetAll",
                "GetByEntity",
                "GetByPKExceptHighest",
                "GetByPKUniqueKeys",
                "IsExisting",
                "MViewRefresh",
                "UpdateByPK",
                "GetByPK"
        ));

        application.setSrcFolder(project.getBasedir().getAbsolutePath()+ "/target/generated-sources/"+application.getName().toLowerCase());
        application.compile(new JavaCompiler());

        return application;
    }
}
