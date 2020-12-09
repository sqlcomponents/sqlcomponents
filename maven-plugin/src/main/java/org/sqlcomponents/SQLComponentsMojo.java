package org.sqlcomponents;

import java.io.File;
import java.io.FileReader;
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
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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

        application.compile(new JavaCompiler());

        getLog().info("Code is compiled into " + application.getSrcFolder());

    }

    private Application getApplication() throws IOException, ScubeException {
        Yaml yaml = new Yaml(new Constructor(Application.class));
        Application application =  yaml.load(new FileReader(project.getBasedir().getAbsolutePath()+File.separator+"sql-components.yml"));

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

        application.setSrcFolder(project.getBasedir().getAbsolutePath()+ File.separator+ "target"+ File.separator+"generated-sources"+ File.separator+application.getName().toLowerCase());


        return application;
    }
}
