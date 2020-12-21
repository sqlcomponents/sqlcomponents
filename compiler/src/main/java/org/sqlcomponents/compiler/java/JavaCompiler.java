package org.sqlcomponents.compiler.java;

import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.base.Template;
import org.sqlcomponents.compiler.java.mapper.JavaMapper;
import org.sqlcomponents.core.compiler.Compiler;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.Entity;
import org.sqlcomponents.core.model.ORM;
import org.sqlcomponents.core.model.Property;
import org.sqlcomponents.core.model.relational.enumeration.Flag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public final class JavaCompiler implements Compiler {

    private Template<Entity> daoTemplate;
    private Template<Entity> beanTemplate;


    public JavaCompiler() {

        try {
            daoTemplate = new Template<>("template/java/jdbcdao.ftl");
            beanTemplate = new Template<>("template/java/bean.ftl");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void compile(Application application) throws ScubeException {
        Mapper mapper = new JavaMapper();
        application.setOrm(mapper.getOrm(application, new Crawler()));
        ORM orm = application.getOrm();



        orm.getEntities().parallelStream().forEach(entity -> {
            try {
                writeDaoImplementation(entity, application.getSrcFolder(), application.getDaoSuffix());
                writeBeanSpecification(entity, application.getSrcFolder(), application.getBeanSuffix());
            } catch (IOException | TemplateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    private void writeDaoImplementation(Entity entity, String srcFolder, String daoSuffix)
            throws IOException, TemplateException {
        String packageFolder = getPackageAsFolder(srcFolder, entity
                .getDaoPackage());
        new File(packageFolder).mkdirs();
        Files.write(new File(packageFolder + File.separator
                        + entity.getName() + "Store" + daoSuffix.trim() + ".java").toPath(),
                getJavaContent(daoTemplate.getContent(entity)).getBytes());




    }

    private void writeBeanSpecification(Entity entity, String srcFolder, String beanSuffix)
            throws IOException, TemplateException {
        String packageFolder = getPackageAsFolder(srcFolder, entity
                .getBeanPackage());
        new File(packageFolder).mkdirs();

        Files.write(new File(packageFolder + File.separator
                        + entity.getName() + (beanSuffix == null ? "" : beanSuffix.trim()) + ".java").toPath(),
                getJavaContent(beanTemplate.getContent(entity)).getBytes());
    }

    /**
     * formats content using google java formatter.
     *
     * @param content
     * @return formattedContent
     */
    private String getJavaContent(final String content) {
        //TODO: Formmater with 1.8
        return content;
    }

    private String getPackageAsFolder(String rootDir, String packageStr) {
        char[] charArray = packageStr.toCharArray();
        StringBuffer filePath = new StringBuffer();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '.') {
                filePath.append(File.separatorChar);
            } else {
                filePath.append(charArray[i]);
            }
        }
        return rootDir + File.separatorChar + filePath.toString();
    }

}
