package org.sqlcomponents.compiler.java;

import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.base.FTLTemplate;
import org.sqlcomponents.compiler.java.mapper.DB2JavaDataTypeMapper;
import org.sqlcomponents.core.compiler.Compiler;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.SQLComponentsException;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.Entity;
import org.sqlcomponents.core.model.ORM;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class JavaFTLCompiler implements Compiler
{
    private FTLTemplate<Application> managerFTLTemplate;
    private FTLTemplate<Entity> storeFTLTemplate;
    private FTLTemplate<Entity> modelFTLTemplate;

    public JavaFTLCompiler() throws IOException
    {
	managerFTLTemplate = new FTLTemplate<>("template/java/Manager.ftl");
	storeFTLTemplate = new FTLTemplate<>("template/java/Store.ftl");
	modelFTLTemplate = new FTLTemplate<>("template/java/Model.ftl");
    }

    @Override
    public void compile(final Application aApplication) throws SQLComponentsException
    {
	Mapper mapper = new DB2JavaDataTypeMapper();
	aApplication.setOrm(mapper.getOrm(aApplication, new Crawler()));
	ORM orm = aApplication.getOrm();

	String packageFolder = getPackageAsFolder(aApplication.getSrcFolder(), aApplication
		.getRootPackage());
	new File(packageFolder).mkdirs();
	try
	{
	    Files.write(new File(packageFolder + File.separator
				 + aApplication.getName() + "Manager" + ".java").toPath(),
			getJavaContent(managerFTLTemplate.getContent(aApplication)).getBytes());
	}
	catch (IOException | TemplateException e)
	{
	    e.printStackTrace();
	}

	orm.getEntities().parallelStream().forEach(entity ->
						   {
						       try
						       {
							   writeDaoImplementation(entity, aApplication.getSrcFolder(), aApplication.getDaoSuffix());
							   writeBeanSpecification(entity, aApplication.getSrcFolder(), aApplication.getBeanSuffix());
						       }
						       catch (final IOException | TemplateException e)
						       {
							   e.printStackTrace();
						       }
						   });

    }

    private void writeDaoImplementation(Entity entity, String srcFolder, String daoSuffix)
	    throws IOException, TemplateException
    {
	String packageFolder = getPackageAsFolder(srcFolder, entity
		.getDaoPackage());
	new File(packageFolder).mkdirs();
	Files.write(new File(packageFolder + File.separator
			     + entity.getName() + "Store" + daoSuffix.trim() + ".java").toPath(),
		    getJavaContent(storeFTLTemplate.getContent(entity)).getBytes());


    }

    private void writeBeanSpecification(Entity entity, String srcFolder, String beanSuffix)
	    throws IOException, TemplateException
    {
	String packageFolder = getPackageAsFolder(srcFolder, entity
		.getBeanPackage());
	new File(packageFolder).mkdirs();

	Files.write(new File(packageFolder + File.separator
			     + entity.getName() + (beanSuffix == null ? "" : beanSuffix.trim()) + ".java").toPath(),
		    getJavaContent(modelFTLTemplate.getContent(entity)).getBytes());
    }

    /**
     * formats content using google java formatter.
     *
     * @return formatted Content
     */
    private String getJavaContent(final String aContent)
    {
	//TODO: format with 1.8
	return aContent;
    }

    private String getPackageAsFolder(final String aRootDir, final String aPackageStr)
    {
	char[] lCharArray = aPackageStr.toCharArray();
	StringBuilder lFilePath = new StringBuilder();
	for (final char aC : lCharArray)
	{
	    if (aC == '.')
	    {
		lFilePath.append(File.separatorChar);
	    }
	    else
	    {
		lFilePath.append(aC);
	    }
	}
	return aRootDir + CoreConsts.BACK_SLASH + lFilePath;
    }
}
