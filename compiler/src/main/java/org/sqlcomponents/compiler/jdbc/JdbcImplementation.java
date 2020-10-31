package org.sqlcomponents.compiler.jdbc;

import org.sqlcomponents.compiler.OrmImplementation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.sqlcomponents.core.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JdbcImplementation extends OrmImplementation {

	@Override
	public void writeImplementation(Application project) {
		ORM orm = project.getOrm();
		ProcessedEntity processedEntity = new ProcessedEntity(orm,project);
		for (Entity entity : orm.getEntities()) {
			try {
				processedEntity.setEntity(entity);
				writeDaoImplementation(processedEntity, project.getSrcFolder(),project.getDaoSuffix());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	private void writeDaoImplementation(ProcessedEntity entity, String srcFolder,String daoSuffix)
			throws IOException, TemplateException {
		String packageFolder = getPackageAsFolder(srcFolder, entity
				.getDaoPackage());
		new File(packageFolder).mkdirs();
		processTemplates(entity, packageFolder + File.separator
				+ entity.getName() + "Store"  + daoSuffix.trim() + ".java", daoTemplate);
	}



	private void processTemplates(Object model, String targetFile,
			Template template) throws IOException, TemplateException {
		FileWriter fileWriter = new FileWriter(targetFile);
		template.process(model, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

	public JdbcImplementation() {
		freeMarkerConfiguration = new Configuration();
		freeMarkerConfiguration.setClassForTemplateLoading(
				JdbcImplementation.class, "/");

		try {
			daoTemplate = freeMarkerConfiguration
					.getTemplate("template/dao/java/jdbc/jdbcdao.ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private final Configuration freeMarkerConfiguration;
	private Template daoTemplate;

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

		public String getTableType() {
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

		public void setTableType(String type) {
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
