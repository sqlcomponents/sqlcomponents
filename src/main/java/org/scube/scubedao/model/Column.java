package org.scube.scubedao.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Column implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnName ;
	private String tableName;
	private String sqlDataType;
	private int size;	
	private int decimalDigits;		
	private String remarks;	
		
	private boolean isNullable;	
	private String uniqueConstraintName ;
	
	private int primaryKeyIndex ;
	
	private List<ForeignKey> foreignKeys ;
	
	// Null if nothing
	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}
	public void setForeignKeys(List<ForeignKey> foreignKeys) {
		if(foreignKeys != null) {
			Collections.sort(foreignKeys);
		}		
		this.foreignKeys = foreignKeys;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSqlDataType() {
		return sqlDataType;
	}
	public void setSqlDataType(String sqlDataType) {
		this.sqlDataType = sqlDataType;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isNullable() {
		return isNullable;
	}
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public String getUniqueConstraintName() {
		return uniqueConstraintName;
	}
	public void setUniqueConstraintName(String uniqueConstraintName) {
		this.uniqueConstraintName = uniqueConstraintName;
	}
	public int getPrimaryKeyIndex() {
		return primaryKeyIndex;
	}
	public void setPrimaryKeyIndex(int primaryKeyIndex) {
		this.primaryKeyIndex = primaryKeyIndex;
	}

}
