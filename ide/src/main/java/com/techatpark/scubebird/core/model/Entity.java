package com.techatpark.scubebird.core.model;

import java.io.Serializable;
import java.util.List;

public class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	private Table table;

	private String name;
	private String pluralName;
	private String beanPackage;
	private String daoPackage;
	
	

	private List<Property> properties;

	public Entity(Table table) {
		setTable(table);
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryName() {
		return table.getCategoryName();
	}

	public String getRemarks() {
		return table.getRemarks();
	}

	public String getSchemaName() {
		return table.getSchemaName();
	}

	public String getTableName() {
		return table.getTableName();
	}

	public String getTableType() {
		return table.getTableType();
	}

	public void setCategoryName(String categoryName) {
		table.setCategoryName(categoryName);
	}

	public void setRemarks(String remarks) {
		table.setRemarks(remarks);
	}

	public void setSchemaName(String schemaName) {
		table.setSchemaName(schemaName);
	}

	public void setTableName(String tableName) {
		table.setTableName(tableName);
	}

	public void setTableType(String type) {
		table.setTableType(type);
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getBeanPackage() {
		return beanPackage;
	}

	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	public String getSequenceName() {
		return table.getSequenceName();
	}

	public void setSequenceName(String sequenceName) {
		table.setSequenceName(sequenceName);
	}

}
