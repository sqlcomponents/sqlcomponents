package org.sqlcomponents.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.sqlcomponents.compiler.java.JavaCompiler;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.IOException;

/**
 * Loads {@code sql-component.yml}, runs {@link JavaCompiler}, and registers the
 * output directory as a compile source root.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        threadSafe = true)
public final class GenerateMojo extends AbstractMojo {

    /**
     * The current Maven project.
     */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /**
     * Path to the YAML configuration (same schema as
     * {@link CoreConsts#buildApplication(File)}).
     */
    @Parameter(property = "sqlcomponents.configFile",
            defaultValue = "${project.basedir}/sql-component.yml")
    private File configFile;

    /**
     * Output directory for generated Java sources. If unset, uses
     * {@code srcFolder} from the YAML when present, otherwise
     * {@code target/generated-sources/sqlcomponents}.
     */
    @Parameter(property = "sqlcomponents.outputDirectory")
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        if (!configFile.isFile()) {
            throw new MojoExecutionException(
                    "SQL Components config not found: "
                            + configFile.getAbsolutePath()
                            + ". Add sql-component.yml at the project base "
                            + "or set sqlcomponents.configFile.");
        }
        final Application application;
        try {
            application = CoreConsts.buildApplication(configFile);
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Failed to read " + configFile.getAbsolutePath(), e);
        }

        final File out = resolveOutputDirectory(application);
        application.setSrcFolder(out.getAbsolutePath());

        try {
            application.compile(new JavaCompiler());
        } catch (Exception e) {
            throw new MojoExecutionException(
                    "SQL Components code generation failed.", e);
        }

        project.addCompileSourceRoot(out.getAbsolutePath());
        getLog().info("SQL Components generated sources: "
                + out.getAbsolutePath());
    }

    private File resolveOutputDirectory(final Application application)
            throws MojoExecutionException {
        if (outputDirectory != null) {
            return absolutize(outputDirectory);
        }
        String fromYaml = application.getSrcFolder();
        if (fromYaml != null && !fromYaml.isBlank()) {
            return absolutize(new File(fromYaml.trim()));
        }
        File def = new File(project.getBuild().getDirectory(),
                "generated-sources/sqlcomponents");
        return def.getAbsoluteFile();
    }

    private File absolutize(final File dir) throws MojoExecutionException {
        File f = dir;
        if (!f.isAbsolute()) {
            f = new File(project.getBasedir(), f.getPath());
        }
        try {
            return f.getCanonicalFile();
        } catch (IOException e) {
            throw new MojoExecutionException("Invalid output path: " + f, e);
        }
    }
}
