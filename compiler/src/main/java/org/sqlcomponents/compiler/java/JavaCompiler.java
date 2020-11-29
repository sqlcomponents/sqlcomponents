package org.sqlcomponents.compiler.java;

import com.google.googlejavaformat.java.FormatterException;
import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.base.Template;
import org.sqlcomponents.compiler.java.mapper.JavaMapper;
import org.sqlcomponents.core.compiler.Compiler;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.*;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enumeration.TableType;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import com.google.googlejavaformat.java.Formatter;

public class JavaCompiler implements Compiler {

	private final Formatter formatter;
	private Template<Entity> daoTemplate;
	private Template<Entity> beanTemplate;

	public JavaCompiler() {
		formatter = new Formatter();

		try {
			daoTemplate = new Template<>("template/dao/java/jdbc/jdbcdao.ftl");

			beanTemplate = new Template<>("template/dao/java/bean.ftl");
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

		orm.getEntities().parallelStream().forEach(entity -> {
			try {
				writeDaoImplementation(entity, application.getSrcFolder(),application.getDaoSuffix());
				writeBeanSpecification(entity, application.getSrcFolder());
			} catch (IOException | FormatterException | TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	private void writeDaoImplementation(Entity entity, String srcFolder,String daoSuffix)
			throws IOException, FormatterException, TemplateException {
		String packageFolder = getPackageAsFolder(srcFolder, entity
				.getDaoPackage());
		new File(packageFolder).mkdirs();
		Files.write(new File(packageFolder + File.separator
						+ entity.getName() + "Store"  + daoSuffix.trim() + ".java").toPath(),
				formatter.formatSource(daoTemplate.getContent(entity)).getBytes());
	}
	private void writeBeanSpecification(Entity entity, String srcFolder)
			throws IOException, FormatterException, TemplateException {
		String packageFolder = getPackageAsFolder(srcFolder, entity
				.getBeanPackage());
		new File(packageFolder).mkdirs();

		Files.write(new File(packageFolder + File.separator
						+ entity.getName() + ".java").toPath(),
				formatter.formatSource(beanTemplate.getContent(entity)).getBytes());
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
				if (highestPKIndex < property.getColumn().getPrimaryKeyIndex()) {
					highestPKIndex = property.getColumn().getPrimaryKeyIndex();
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
