package org.sqlcomponents.compiler.java;

import freemarker.template.TemplateException;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.sqlcomponents.compiler.template.FTLTemplate;
import org.sqlcomponents.compiler.mapper.JavaMapper;
import org.sqlcomponents.core.compiler.Compiler;
import org.sqlcomponents.core.crawler.util.DataSourceUtil;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.Entity;
import org.sqlcomponents.core.model.ORM;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * The type Java compiler.
 */
public final class JavaCompiler implements Compiler {
    /**
     * The constant DOT_JAVA.
     */
    private static final String DOT_JAVA = ".java";
    /**
     * The Manager ftl template.
     */
    private final FTLTemplate<Application> managerFTLTemplate;
    /**
     * The Store ftl template.
     */
    private final FTLTemplate<Entity> storeFTLTemplate;
    /**
     * The Model ftl template.
     */
    private final FTLTemplate<Entity> modelFTLTemplate;

    /**
     * Instantiates a new Java compiler.
     *
     * @throws IOException the io exception
     */
    public JavaCompiler() throws IOException {
        managerFTLTemplate = new FTLTemplate<>("template/java/Manager.ftl");
        storeFTLTemplate = new FTLTemplate<>("template/java/Store.ftl");
        modelFTLTemplate = new FTLTemplate<>("template/java/Model.ftl");
    }

    @Override
    public void compile(final Application aApplication) throws SQLException {

        runFlywayMigrations(aApplication);

        Mapper mapper = new JavaMapper(aApplication);
        aApplication.setOrm(mapper.getOrm());
        ORM orm = aApplication.getOrm();

        String packageFolder = getPackageAsFolder(aApplication.getSrcFolder(),
                aApplication.getRootPackage());
        new File(packageFolder).mkdirs();
        try {
            Files.write(
                    new File(packageFolder + File.separator
                            + "DataManager"
                            + DOT_JAVA).toPath(),
                    getJavaContent(managerFTLTemplate.getContent(
                            aApplication)).getBytes());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        orm.getEntities().parallelStream().forEach(entity -> {
            try {
                writeDaoImplementation(entity, aApplication.getSrcFolder());
                writeBeanSpecification(entity, aApplication.getSrcFolder());
            } catch (final IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Run Flyway Migrations if Flyway is in the classpath.
     * @param application
     */
    private void runFlywayMigrations(final Application application) {
        if (Class.forName(
                JavaCompiler.class.getModule(),
                "org.flywaydb.core.Flyway") != null) {

            String filePath = "src/main/resources/db/migration";

            if (new File(filePath).exists()) {

                System.out.println("Hay Man "
                        + new File(filePath).getAbsolutePath());

                FluentConfiguration fluentConfiguration =
                        new FluentConfiguration(ClassLoader
                                .getSystemClassLoader());

                fluentConfiguration
                        .locations("filesystem:" + filePath);

                fluentConfiguration
                        .dataSource(DataSourceUtil.getDataSource(application))
                        .load().migrate();
            }



        }
    }

    /**
     * Write dao implementation.
     *
     * @param entity    the entity
     * @param srcFolder the src folder
     * @throws IOException       the io exception
     * @throws TemplateException the template exception
     */
    private void writeDaoImplementation(final Entity entity,
                                        final String srcFolder)
            throws IOException, TemplateException {
        String packageFolder =
                getPackageAsFolder(srcFolder, entity.getDaoPackage());
        new File(packageFolder).mkdirs();
        Files.write(new File(
                        packageFolder + File.separator + entity.getName()
                                + "Store"
                                + DOT_JAVA).toPath(),
                getJavaContent(storeFTLTemplate.getContent(entity)).getBytes());
    }

    /**
     * Write bean specification.
     *
     * @param aEntity    the a entity
     * @param aSrcFolder the a src folder
     * @throws IOException       the io exception
     * @throws TemplateException the template exception
     */
    private void writeBeanSpecification(final Entity aEntity,
                                        final String aSrcFolder)
            throws IOException, TemplateException {
        String packageFolder =
                getPackageAsFolder(aSrcFolder, aEntity.getBeanPackage());
        new File(packageFolder).mkdirs();

        Files.write(new File(
                        packageFolder + File.separator + aEntity.getName()
                                + DOT_JAVA).toPath(),
                getJavaContent(
                        modelFTLTemplate.getContent(aEntity)).getBytes());
    }

    /**
     * formats content using google java formatter.
     *
     * @param aContent the a content
     * @return formatted Content
     */
    private String getJavaContent(final String aContent) {
        return aContent;
    }

    /**
     * Gets package as folder.
     *
     * @param aRootDir    the a root dir
     * @param aPackageStr the a package str
     * @return the package as folder
     */
    private String getPackageAsFolder(final String aRootDir,
                                      final String aPackageStr) {
        char[] lCharArray = aPackageStr.toCharArray();
        StringBuilder lFilePath = new StringBuilder();
        for (final char aC : lCharArray) {
            if (aC == '.') {
                lFilePath.append(File.separatorChar);
            } else {
                lFilePath.append(aC);
            }
        }
        return aRootDir + CoreConsts.BACK_SLASH + lFilePath;
    }
}
