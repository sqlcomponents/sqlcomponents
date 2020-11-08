package org.sqlcomponents.compiler.java;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.java.mapper.JavaMapper;
import org.sqlcomponents.core.compiler.Compiler;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.*;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enumeration.TableType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaCompiler implements Compiler {

	private final Configuration cfg;
	private Template daoTemplate;
	private Template beanTemplate;

	public JavaCompiler() {
		cfg = new Configuration(Configuration.VERSION_2_3_29);
		cfg.setClassForTemplateLoading(
				JavaCompiler.class, "/");
		try {
			daoTemplate = cfg
					.getTemplate("template/dao/java/jdbc/jdbcdao.ftl");
			beanTemplate = cfg.getTemplate("template/dao/java/bean.ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void compile(Application application) throws ScubeException {
		Mapper mapper = new JavaMapper();
		application.setOrm(mapper.getOrm(application,new Crawler()));
		ORM orm = application.getOrm();
		for (Entity entity : orm.getEntities()) {
			try {
				writeDaoImplementation(entity, application.getSrcFolder(),application.getDaoSuffix());
				writeBeanSpecification(entity, application.getSrcFolder());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void writeDaoImplementation(Entity entity, String srcFolder,String daoSuffix)
			throws IOException, TemplateException {
		String packageFolder = getPackageAsFolder(srcFolder, entity
				.getDaoPackage());
		new File(packageFolder).mkdirs();
		processTemplates(entity, packageFolder + File.separator
				+ entity.getName() + "Store"  + daoSuffix.trim() + ".java", daoTemplate);
	}
	private void writeBeanSpecification(Entity entity, String srcFolder)
			throws IOException, TemplateException {
		String packageFolder = getPackageAsFolder(srcFolder, entity
				.getBeanPackage());
		new File(packageFolder).mkdirs();
		processTemplates(entity, packageFolder + File.separator
				+ entity.getName() + ".java", beanTemplate);
	}


	private void processTemplates(Object model, String targetFile,
			Template template) throws IOException, TemplateException {
		FileWriter fileWriter = new FileWriter(targetFile);
		template.process(model, fileWriter);
		fileWriter.flush();
		fileWriter.close();
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


	public static class ProcessedEntity {

		private Entity entity;

		public List<String> getUniqueConstraintGroupNames() {
			List<String> uniqueConstraintGroupNames = new ArrayList<String>() ;
			String prevUniqueConstraintGroupName = null ;
			String uniqueConstraintGroupName = null ;
			for (Property property : getProperties()) {
				uniqueConstraintGroupName = property.getUniqueConstraintGroup() ;
				if( uniqueConstraintGroupName!=null 
						&& !uniqueConstraintGroupName.equals(prevUniqueConstraintGroupName)) {
					uniqueConstraintGroupNames.add(uniqueConstraintGroupName);
					prevUniqueConstraintGroupName = uniqueConstraintGroupName ;
				}
			}
			return uniqueConstraintGroupNames ;
		}

		public int getHighestPKIndex() {
			int highestPKIndex = 0;
			for (Property property : getProperties()) {
				if (highestPKIndex < property.getPrimaryKeyIndex()) {
					highestPKIndex = property.getPrimaryKeyIndex();
				}
			}
			return highestPKIndex;
		}

		private ORM orm;

		private Application ormApplication;

		public Application getOrmDaoProject() {
			return ormApplication;
		}

		public void setOrmDaoProject(final Application ormApplication) {
			this.ormApplication = ormApplication;
		}

		public ProcessedEntity(ORM orm, Application ormApplication) {
			setOrm(orm);
			setOrmDaoProject(ormApplication);
		}

		public ORM getOrm() {
			return orm;
		}

		private void setOrm(ORM orm) {
			this.orm = orm;
		}

		public Entity getEntity() {
			return entity;
		}

		public void setEntity(Entity entity) {
			this.entity = entity;
		}

		public String getBeanPackage() {
			return entity.getBeanPackage();
		}

		public String getCategoryName() {
			return entity.getTable().getCategoryName();
		}

		public String getDaoPackage() {
			return entity.getDaoPackage();
		}

		public String getName() {
			return entity.getName();
		}

		public String getPluralName() {
			return entity.getPluralName();
		}

		public List<Property> getProperties() {
			return entity.getProperties();
		}

		public String getRemarks() {
			return entity.getTable().getRemarks();
		}

		public String getSchemaName() {
			return entity.getTable().getSchemaName();
		}

		public Table getTable() {
			return entity.getTable();
		}

		public String getTableName() {
			return entity.getTable().getTableName();
		}

		public TableType getTableType() {
			return entity.getTable().getTableType();
		}

		public void setBeanPackage(String beanPackage) {
			entity.setBeanPackage(beanPackage);
		}

		public void setCategoryName(String categoryName) {
			entity.getTable().setCategoryName(categoryName);
		}

		public void setDaoPackage(String daoPackage) {
			entity.setDaoPackage(daoPackage);
		}

		public void setName(String name) {
			entity.setName(name);
		}

		public void setPluralName(String pluralName) {
			entity.setPluralName(pluralName);
		}

		public void setProperties(List<Property> properties) {
			entity.setProperties(properties);
		}

		public void setRemarks(String remarks) {
			entity.getTable().setRemarks(remarks);
		}

		public void setSchemaName(String schemaName) {
			entity.getTable().setSchemaName(schemaName);
		}

		public void setTable(Table table) {
			entity.setTable(table);
		}

		public void setTableName(String tableName) {
			entity.getTable().setTableName(tableName);
		}

		public void setTableType(TableType type) {
			entity.getTable().setTableType(type);
		}

		public String getDriverName() {
			return ormApplication.getDriverName();
		}

		public void setDriverName(final String driverName) {
			ormApplication.setDriverName(driverName);
		}
		public String getSequenceName() {
			return entity.getTable().getSequenceName();
		}


	}

}
