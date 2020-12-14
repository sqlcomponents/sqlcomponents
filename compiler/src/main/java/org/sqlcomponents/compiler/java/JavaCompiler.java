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
    private Template<Application> fieldTemplate;
    private Template<Application> whereTemplate;
    private Template<Application> partialWhereClauseTemplate;
    private Map<String,Template<Property>> fieldsTemplate;

    public JavaCompiler() {

        try {
            daoTemplate = new Template<>("template/java/jdbcdao.ftl");
            beanTemplate = new Template<>("template/java/bean.ftl");
            fieldTemplate = new Template<>("template/java/Field.ftl");
            whereTemplate = new Template<>("template/java/WhereClause.ftl");
            partialWhereClauseTemplate = new Template<>("template/java/PartialWhereClause.ftl");
            fieldsTemplate = new HashMap<>();
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

        // Common Classes
        String commonPackageFolder = getPackageAsFolder(application.getSrcFolder()
                , application.getRootPackage()+".common");
        new File(commonPackageFolder).mkdirs();
        try {
            Files.write(new File(commonPackageFolder+ File.separator + "Field.java").toPath(),
                    getJavaContent(fieldTemplate.getContent(application)).getBytes());
            Files.write(new File(commonPackageFolder+ File.separator + "WhereClause.java").toPath(),
                    getJavaContent(whereTemplate.getContent(application)).getBytes());
            Files.write(new File(commonPackageFolder+ File.separator + "PartialWhereClause.java").toPath(),
                    getJavaContent(partialWhereClauseTemplate.getContent(application)).getBytes());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

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


        entity.getProperties().stream().forEach(property -> {
            Template<Property> template = fieldsTemplate.get(property.getDataType());
            if(template == null ) {

                try{
                    String dataTypeClass = property.getDataType();
                    if(Class.forName(dataTypeClass).getSuperclass().equals(Number.class)) {
                        dataTypeClass = "java.lang.Number";
                    }

                    String fieldPackageFolder = getPackageAsFolder("template/java/field", dataTypeClass)+ "Field.ftl";

                    template = new Template<>(fieldPackageFolder);
                    fieldsTemplate.put(property.getDataType(),template);



                    File file = new File(getPackageAsFolder(srcFolder, entity
                            .getOrm().getApplication().getRootPackage() + ".common.field") + File.separator
                            + (property.getColumn().getNullable() == Flag.YES ? "Nullable" : "" )
                            + dataTypeClass.substring(dataTypeClass.lastIndexOf('.')+1) + "Field.java");
                    if(!file.exists()) {
                        file.getParentFile().mkdirs();
                        Files.write(file.toPath(),
                                getJavaContent(template.getContent(property)).getBytes());
                    }else {
                        System.out.println(property.getName() + template + " => " + file.getAbsolutePath());

                    }


                } catch (IOException | ClassNotFoundException | TemplateException e) {
                    e.printStackTrace();
                }

            }

        });

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
