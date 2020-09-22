package org.scube.scubedao.mapper;

import org.scube.exception.ScubeException;
import org.scube.scubedao.constants.ApplicationConstants;
import org.scube.scubedao.crawler.Crawler;
import org.scube.scubedao.mapper.java.JavaOrmMapper;
import org.scube.scubedao.model.Package;
import org.scube.scubedao.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Mapper {

	public static Mapper getMapper(DaoProject project) {
		return new JavaOrmMapper();
	}

	public abstract String[] getValidDataTypes(String sqlType, int size,
			int precision);

	public abstract String getValidDataType(String sqlType, int size,
			int precision);

	public ORM getOrm(DaoProject ormProject) throws ScubeException {

		ORM orm = ormProject.getOrm();
		Crawler crawler = Crawler.getCrawler(ormProject);

		Schema schema = null;

		if (ormProject.getOrm().getSchema() == null || ormProject.isOnline()) {
			schema = crawler.getSchema(ormProject);
			ormProject.getOrm().setSchema(schema);
		} else {
			schema = ormProject.getOrm().getSchema();
		}

		orm.setEntities(getEntities(ormProject));
		orm.setMethods(getMethods(ormProject));
		orm.setServices(getServices(ormProject));
		return orm;
	}

	private Method getMethod(Function function, DaoProject ormProject) {
		List<Property> properties = new ArrayList<Property>(function
				.getParametes().size());
		Method method = new Method(function);
		method.setName(getPropertyName(ormProject, function.getFunctionName()));

		for (Column column : function.getParametes()) {
			properties.add(getProperty(ormProject, column));
		}
		method.setInputParameters(properties);

		method.setOutputProperty(getProperty(ormProject, function.getOutput()));
		return method;
	}

	private List<Method> getMethods(DaoProject ormProject) {
		Schema schema = ormProject.getSchema();
		ArrayList<Method> methods = new ArrayList<Method>(schema.getFunctions()
				.size());

		for (Function function : schema.getFunctions()) {

			methods.add(getMethod(function, ormProject));
		}

		return methods;
	}

	private List<Service> getServices(DaoProject ormProject) {

		ArrayList<Service> services = new ArrayList<Service>(ormProject
				.getSchema().getPackages().size());
		Service service = null;
		for (Package package1 : ormProject.getSchema().getPackages()) {
			service = new Service();
			
			service.setPackage(package1);
			service.setServiceName(getServiceName(ormProject, service.getName()));
			service.setDaoPackage(getDaoPackage(ormProject, service.getName())) ;
			service.setMethods(new ArrayList<Method>());
			for (Function function : package1.getFunctions()) {
				service.getMethods().add(getMethod(function, ormProject));
			}
			services.add(service) ;
		}

		return services;
	}

	private Property getProperty(DaoProject ormProject, Column column) {
		if (column != null) {
			Property property = new Property(column);
			if (column.getColumnName() != null) {
				property.setName(getPropertyName(ormProject, column
						.getColumnName()));
			}
			property.setUniqueConstraintGroup(getEntityName(ormProject,
					property.getUniqueConstraintName()));
			property.setDataType(getValidDataType(property.getSqlDataType(),
					property.getSize(), property.getDecimalDigits()));
			return property;
		}
		return null;

	}

	private List<Entity> getEntities(DaoProject ormProject) {

		Schema schema = ormProject.getSchema();

		ArrayList<Entity> entities = new ArrayList<Entity>(schema.getTables()
				.size());

		List<Property> properties;
		Entity entity;

		for (Table table : schema.getTables()) {
			entity = new Entity(table);
			entity.setName(getEntityName(ormProject, table.getTableName()));
			entity.setPluralName(getPluralName(ormProject, entity.getName()));
			entity
					.setDaoPackage(getDaoPackage(ormProject, table
							.getTableName()));
			entity.setBeanPackage(getBeanPackage(ormProject, table
					.getTableName()));
			entity
					.setSequenceName(getSequenceName(ormProject, entity
							.getName()));

			properties = new ArrayList<Property>(table.getColumns().size());

			for (Column column : table.getColumns()) {
				properties.add(getProperty(ormProject, column));
			}
			entity.setProperties(properties);
			entities.add(entity);
		}
		return entities;
	}

	private String getSequenceName(DaoProject ormProject, String entityName) {
		List<String> sequences = ormProject.getSchema().getSequences();
		for (String sequence : sequences) {
			if (entityName.equals(getEntityName(ormProject, sequence))) {
				return sequence;
			}
		}
		return null;
	}

	protected String getServiceName(DaoProject ormProject, String packageName) {
		if (packageName != null) {
			StringBuffer buffer = new StringBuffer();
			String[] relationalWords = packageName
					.split(ApplicationConstants.DB_WORD_SEPARATOR);
			int relationalWordsCount = relationalWords.length;
			for (int index = 0; index < relationalWordsCount; index++) {
				buffer.append(toTileCase(getObjectOrientedWord(ormProject,
						relationalWords[index])));
			}
			buffer.append("Service") ;
			return buffer.toString();
		}
		return null;
	}

	protected String getEntityName(DaoProject ormProject, String tableName) {
		if (tableName != null) {
			StringBuffer buffer = new StringBuffer();
			String[] relationalWords = tableName
					.split(ApplicationConstants.DB_WORD_SEPARATOR);
			int relationalWordsCount = relationalWords.length;
			for (int index = 0; index < relationalWordsCount; index++) {
				buffer.append(toTileCase(getObjectOrientedWord(ormProject,
						relationalWords[index])));
			}
			if(ormProject.getBeanSuffix() != null && ormProject.getBeanSuffix().trim().length() != 0) {
				buffer.append(ormProject.getBeanSuffix().trim());
			}
			return buffer.toString();
		}
		return null;
	}

	protected String getObjectOrientedWord(DaoProject ormProject,
			String relationalWord) {
		String objectOrientedWord = null;
		if (ormProject.getWordsMap() != null) {
			for (String relationalWordKey : ormProject.getWordsMap().keySet()) {
				if (relationalWord.equalsIgnoreCase(relationalWordKey)) {
					objectOrientedWord = ormProject.getWordsMap().get(
							relationalWordKey);
				}

			}
		}
		return objectOrientedWord == null ? relationalWord : objectOrientedWord;

	}

	protected String getPluralName(DaoProject ormProject, String entityName) {
		String pluralName = null;
		HashMap<String, String> pluralMap = ormProject.getPluralMap();
		String pluralValue;
		String capsEntityName = entityName.toUpperCase();
		if (pluralMap != null && pluralMap.size() != 0) {
			for (String pluralKey : pluralMap.keySet()) {
				pluralValue = pluralMap.get(pluralKey).toUpperCase();
				if (capsEntityName.endsWith(pluralKey.toUpperCase())) {
					int lastIndex = capsEntityName.lastIndexOf(pluralKey
							.toUpperCase());
					pluralName = entityName.substring(0, lastIndex)
							+ toTileCase(pluralValue);
					break;
				}
			}
		}
		if (pluralName == null) {
			pluralName = entityName + "s";
		}
		return pluralName;
	}

	
	private String getPackage(DaoProject ormProject, String tableName,String identifier) {
		StringBuffer buffer = new StringBuffer();
		
		if (ormProject.getRootPackage() != null) {
			buffer.append(ormProject.getRootPackage());
		}

		String moduleName = getModuleName(ormProject, tableName);
		
		if (ormProject.isModulesFirst()) {
			if (moduleName != null &&
					moduleName.trim().length() != 0) {
				buffer.append(".") ;
				buffer.append(moduleName.trim());
			}
			if(identifier != null
					&& identifier.trim().length() != 0) {
				buffer.append(".") ;
				buffer.append(identifier.trim());
			}
		}else {
			if(identifier != null
					&& identifier.trim().length() != 0) {
				buffer.append(".") ;
				buffer.append(identifier.trim());
			}
			if (moduleName != null &&
					moduleName.trim().length() != 0) {
				buffer.append(".") ;
				buffer.append(moduleName.trim());
			}
		}
		return buffer.toString().toLowerCase();
	}
	protected String getDaoPackage(DaoProject ormProject, String tableName) {
		return getPackage(ormProject, tableName, ormProject.getDaoIdentifier());
	}

	protected String getBeanPackage(DaoProject ormProject, String tableName) {
		return getPackage(ormProject, tableName, ormProject.getBeanIdentifier());
	}

	protected String getModuleName(DaoProject ormProject, String tableName) {

		String[] dbWords = tableName
				.split(ApplicationConstants.DB_WORD_SEPARATOR);
		HashMap<String, String> modulesMap = ormProject.getModulesMap();
		if (modulesMap != null) {
			for (String moduleKey : modulesMap.keySet()) {
				for (int i = dbWords.length - 1; i >= 0; i--) {
					if (dbWords[i].toUpperCase()
							.equals(moduleKey.toUpperCase())) {
						return modulesMap.get(moduleKey);

					}
				}
			}
		}
		return null;
	}

	protected String getPropertyName(DaoProject ormProject, String columnName) {
		StringBuffer buffer = new StringBuffer();
		String[] relationalWords = columnName
				.split(ApplicationConstants.DB_WORD_SEPARATOR);
		int relationalWordsCount = relationalWords.length;
		for (int index = 0; index < relationalWordsCount; index++) {
			if (index == 0) {
				buffer.append(getObjectOrientedWord(ormProject,
						relationalWords[index]).toLowerCase());
			} else {
				buffer.append(toTileCase(getObjectOrientedWord(ormProject,
						relationalWords[index])));
			}
		}
		return buffer.toString();
	}

	protected String toTileCase(String word) {
		char[] wordTemp = word.toLowerCase().toCharArray();
		int letterCount = wordTemp.length;
		for (int index = 0; index < letterCount; index++) {
			if (index == 0) {
				wordTemp[index] = Character.toUpperCase(wordTemp[index]);
			} else {
				wordTemp[index] = Character.toLowerCase(wordTemp[index]);
			}
		}
		return new String(wordTemp);
	}
}