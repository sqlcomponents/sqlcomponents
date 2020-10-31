package org.sqlcomponents.core.model;



import org.sqlcomponents.core.model.relational.Database;

import java.util.HashMap;
import java.util.List;

public class ORM  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	private String password;

	private String schemaName;

	private Database database;

	private String url;
	
	private String daoIdentifier;

	private String beanIdentifier;
	
	private String daoSuffix;

	private String beanSuffix;

	public String getDaoIdentifier() {
		return daoIdentifier;
	}

	public void setDaoIdentifier(String daoIdentifier) {
		this.daoIdentifier = daoIdentifier;
	}

	public String getBeanIdentifier() {
		return beanIdentifier;
	}

	public void setBeanIdentifier(String beanIdentifier) {
		this.beanIdentifier = beanIdentifier;
	}

	public String getDaoSuffix() {
		return daoSuffix;
	}

	public void setDaoSuffix(String daoSuffix) {
		this.daoSuffix = daoSuffix;
	}

	public String getBeanSuffix() {
		return beanSuffix;
	}

	public void setBeanSuffix(String beanSuffix) {
		this.beanSuffix = beanSuffix;
	}

	private HashMap<String, String> wordsMap;

	private HashMap<String, String> modulesMap;

	private HashMap<String, String> updateMap;

	private HashMap<String, String> insertMap;
	
	private HashMap<String, String> validationMap;

	public HashMap<String, String> getValidationMap() {
		return validationMap;
	}

	public void setValidationMap(HashMap<String, String> validationMap) {
		this.validationMap = validationMap;
	}

	private List<Entity> entities;
	
	private List<Service> services ;

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	private List<Method> methods;

	private List<Default> defaults;

	private boolean pagination ;
	
	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public List<Default> getDefaults() {
		return defaults;
	}

	public void setDefaults(List<Default> defaults) {
		this.defaults = defaults;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	private List<String> methodSpecification;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Database getSchema() {
		return database;
	}

	public void setSchema(Database database) {
		this.database = database;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public HashMap<String, String> getWordsMap() {
		return wordsMap;
	}

	public void setWordsMap(HashMap<String, String> wordsMap) {
		this.wordsMap = wordsMap;
	}

	public HashMap<String, String> getModulesMap() {
		return modulesMap;
	}

	public void setModulesMap(HashMap<String, String> modulesMap) {
		this.modulesMap = modulesMap;
	}

	public HashMap<String, String> getUpdateMap() {
		return updateMap;
	}

	public void setUpdateMap(HashMap<String, String> updateMap) {
		this.updateMap = updateMap;
	}

	public HashMap<String, String> getInsertMap() {
		return insertMap;
	}

	public void setInsertMap(HashMap<String, String> insertMap) {
		this.insertMap = insertMap;
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	public Entity getEntity(String tableName) {
		Entity entity = null ;
		for (Entity entity2 : getEntities()) {
			if(entity2.getTable().getTableName().equals(tableName)) {
				return entity2;
			}
		}
		return entity;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public List<String> getMethodSpecification() {
		return methodSpecification;
	}

	public void setMethodSpecification(List<String> methodSpecification) {
		this.methodSpecification = methodSpecification;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

}
