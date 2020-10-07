package com.techatpark.scubebird.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class DaoProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String location;

	private String srcFolder;

	private List<String> tablePatterns;

	private List<String> sequencePatterns;

	public List<String> getSequencePatterns() {
		return sequencePatterns;
	}

	public void setSequencePatterns(List<String> sequencePatterns) {
		this.sequencePatterns = sequencePatterns;
	}

	private String rootPackage;

	private HashMap<String, String> wordsMap;

	private HashMap<String, String> modulesMap;

	private HashMap<String, String> pluralMap;

	private String sequenceTableMap;

	public String getBeanSuffix() {
		return orm.getBeanSuffix();
	}

	public String getDaoSuffix() {
		return orm.getDaoSuffix();
	}

	public void setBeanSuffix(String beanSuffix) {
		orm.setBeanSuffix(beanSuffix);
	}

	public void setDaoSuffix(String daoSuffix) {
		orm.setDaoSuffix(daoSuffix);
	}

	private boolean online;

	private boolean modulesFirst;

	private boolean cleanSource;

	public boolean isCleanSource() {
		return cleanSource;
	}

	public void setCleanSource(boolean cleanSource) {
		this.cleanSource = cleanSource;
	}

	private ORM orm;

	public DaoProject() {
		setOrm(new ORM());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getTablePatterns() {
		return tablePatterns;
	}

	public void setTablePatterns(List<String> tablePatterns) {
		this.tablePatterns = tablePatterns;
	}

	public String getRootPackage() {
		return rootPackage;
	}

	public void setRootPackage(String rootPackage) {
		this.rootPackage = rootPackage;
	}

	public HashMap<String, String> getWordsMap() {
		return wordsMap;
	}

	public String getBeanIdentifier() {
		return orm.getBeanIdentifier();
	}

	public String getDaoIdentifier() {
		return orm.getDaoIdentifier();
	}

	public void setBeanIdentifier(String beanIdentifier) {
		orm.setBeanIdentifier(beanIdentifier);
	}

	public void setDaoIdentifier(String daoIdentifier) {
		orm.setDaoIdentifier(daoIdentifier);
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

	public String getSequenceTableMap() {
		return sequenceTableMap;
	}

	public void setSequenceTableMap(String sequenceTableMap) {
		this.sequenceTableMap = sequenceTableMap;
	}

	public ORM getOrm() {
		return orm;
	}

	public void setOrm(ORM orm) {
		this.orm = orm;
	}

	public List<Entity> getEntities() {
		return orm.getEntities();
	}

	public HashMap<String, String> getInsertMap() {
		return orm.getInsertMap();
	}

	public String getPassword() {
		return orm.getPassword();
	}

	public Schema getSchema() {
		return orm.getSchema();
	}

	public HashMap<String, String> getUpdateMap() {
		return orm.getUpdateMap();
	}

	public String getUrl() {
		return orm.getUrl();
	}

	public String getUserName() {
		return orm.getUserName();
	}

	public int hashCode() {
		return orm.hashCode();
	}

	public void setEntities(List<Entity> entities) {
		orm.setEntities(entities);
	}

	public void setInsertMap(HashMap<String, String> insertMap) {
		orm.setInsertMap(insertMap);
	}

	public List<String> getMethodSpecification() {
		return orm.getMethodSpecification();
	}

	public void setMethodSpecification(List<String> methodSpecification) {
		orm.setMethodSpecification(methodSpecification);
	}

	public void setPassword(String password) {
		orm.setPassword(password);
	}

	public void setSchema(Schema schema) {
		orm.setSchema(schema);
	}

	public void setUpdateMap(HashMap<String, String> updateMap) {
		orm.setUpdateMap(updateMap);
	}

	public void setUrl(String url) {
		orm.setUrl(url);
	}

	public void setUserName(String userName) {
		orm.setUserName(userName);
	}

	public String toString() {
		return name;
	}

	public String getSchemaName() {
		return orm.getSchemaName();
	}

	public void setSchemaName(String schemaName) {
		orm.setSchemaName(schemaName);
	}

	public HashMap<String, String> getPluralMap() {
		return pluralMap;
	}

	public void setPluralMap(HashMap<String, String> pluralMap) {
		this.pluralMap = pluralMap;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isModulesFirst() {
		return modulesFirst;
	}

	public void setModulesFirst(boolean modulesFirst) {
		this.modulesFirst = modulesFirst;
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

}
