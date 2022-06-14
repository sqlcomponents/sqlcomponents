package org.sqlcomponents;

import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.sqlcomponents.compiler.java.JavaFTLCompiler;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.IOException;

@Mojo(name = SQLComponentsMojo.GENERATED_SOURCES, defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public final class SQLComponentsMojo extends AbstractMojo {
    static final String GENERATED_SOURCES = "generated-sources";
    private static final String GENERATE_SOURCES_DIR = CoreConsts.BACK_SLASH + GENERATED_SOURCES + CoreConsts.BACK_SLASH;
    private static final String YML_SPEC_FILE_NAME = "sql-components.yml";

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @SneakyThrows
    public void execute() {
        Application lApplication = createApplicationFromYMLSpec();
        lApplication.compile(new JavaFTLCompiler()); //todo: why compiler has to be passed, why not created within compile method, is Compiler Injectable?
    }

    //todo: what if the name of yaml file customization to be supported?
    private Application createApplicationFromYMLSpec() throws IOException {

        Application lApplication = CoreConsts.buildApplication(new File(project.getBasedir(), YML_SPEC_FILE_NAME));

        lApplication.setSrcFolder(project.getBuild().getDirectory() + GENERATE_SOURCES_DIR + lApplication.getName().toLowerCase());
        getLog().info("'" + GENERATED_SOURCES + "' directory is: " + lApplication.getSrcFolder());

        project.addCompileSourceRoot(lApplication.getSrcFolder());

        return lApplication;
    }
}
