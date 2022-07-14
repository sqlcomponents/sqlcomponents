package org.sqlcomponents.compiler.java;

import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.base.FTLTemplate;
import org.sqlcomponents.compiler.java.mapper.DB2JavaDataTypeMapper;
import org.sqlcomponents.core.compiler.Compiler;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.Entity;
import org.sqlcomponents.core.model.ORM;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public final class JavaFTLCompiler implements Compiler
{
    private static final String DOT_JAVA = ".java";
    private final FTLTemplate<Application> managerFTLTemplate;
    private final FTLTemplate<Entity> storeFTLTemplate;
    private final FTLTemplate<Entity> modelFTLTemplate;

    public JavaFTLCompiler() throws IOException
    {
	managerFTLTemplate = new FTLTemplate<>("template/java/Manager.ftl");
	storeFTLTemplate = new FTLTemplate<>("template/java/Store.ftl");
	modelFTLTemplate = new FTLTemplate<>("template/java/Model.ftl");
    }

    @Override
    public void compile(final Application aApplication) throws SQLException
    {
	Mapper mapper = new DB2JavaDataTypeMapper(aApplication);
	aApplication.setOrm(mapper.getOrm());
	ORM orm = aApplication.getOrm();

	String packageFolder = getPackageAsFolder(aApplication.getSrcFolder(), aApplication.getRootPackage());
	new File(packageFolder).mkdirs();
	try
	{
	    Files.write(new File(packageFolder + File.separator
				 + aApplication.getName() + "Manager" + DOT_JAVA).toPath(),
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
							   writeDaoImplementation(entity, aApplication.getSrcFolder(),
										  aApplication.getDaoSuffix());
							   writeBeanSpecification(entity, aApplication.getSrcFolder(),
										  aApplication.getBeanSuffix());
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
	Files.write(new File(
			    packageFolder + File.separator + entity.getName() + "Store" + daoSuffix.trim() + DOT_JAVA).toPath(),
		    getJavaContent(storeFTLTemplate.getContent(entity)).getBytes());
    }

    private void writeBeanSpecification(final Entity aEntity, final String aSrcFolder, final String aBeanSuffix)
	    throws IOException, TemplateException
    {
	String packageFolder = getPackageAsFolder(aSrcFolder, aEntity.getBeanPackage());
	new File(packageFolder).mkdirs();

	Files.write(new File(packageFolder + File.separator
			     + aEntity.getName() + (aBeanSuffix == null ? "" : aBeanSuffix.trim()) + DOT_JAVA).toPath(),
		    getJavaContent(modelFTLTemplate.getContent(aEntity)).getBytes());
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
