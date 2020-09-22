package com.techatpark.scubebird.scubedao.model;

import java.io.Serializable;
import java.util.List;

public class Property implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Property(Column column) {
		setColumn(column) ;
	}
	
	private Column column ;
		
	private String name ;	
	
	private String dataType;
	
	private String uniqueConstraintGroup ;

	public String getUniqueConstraintGroup() {
		return uniqueConstraintGroup;
	}

	public void setUniqueConstraintGroup(String uniqueConstraintGroup) {
		this.uniqueConstraintGroup = uniqueConstraintGroup;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnName() {
		return column.getColumnName();
	}

	public int getDecimalDigits() {
		return column.getDecimalDigits();
	}

	public int getPrimaryKeyIndex() {
		return column.getPrimaryKeyIndex();
	}

	public String getRemarks() {
		return column.getRemarks();
	}

	public int getSize() {
		return column.getSize();
	}

	public String getSqlDataType() {
		return column.getSqlDataType();
	}

	public String getTableName() {
		return column.getTableName();
	}

	public boolean isNullable() {
		return column.isNullable();
	}	

	public String getUniqueConstraintName() {
		return column.getUniqueConstraintName();
	}

	public void setUniqueConstraintName(String uniqueConstraintName) {
		column.setUniqueConstraintName(uniqueConstraintName);
	}

	public void setColumnName(String columnName) {
		column.setColumnName(columnName);
	}

	public void setDecimalDigits(int decimalDigits) {
		column.setDecimalDigits(decimalDigits);
	}

	public void setNullable(boolean isNullable) {
		column.setNullable(isNullable);
	}

	public void setPrimaryKeyIndex(int primaryKeyIndex) {
		column.setPrimaryKeyIndex(primaryKeyIndex);
	}



	public List<ForeignKey> getForeignKeys() {
		return column.getForeignKeys();
	}

	public void setForeignKeys(List<ForeignKey> foreignKeys) {
		column.setForeignKeys(foreignKeys);
	}

	public void setRemarks(String remarks) {
		column.setRemarks(remarks);
	}

	public void setSize(int size) {
		column.setSize(size);
	}

	public void setSqlDataType(String sqlDataType) {
		column.setSqlDataType(sqlDataType);
	}

	public void setTableName(String tableName) {
		column.setTableName(tableName);
	}
	
}
