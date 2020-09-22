package com.techatpark.scubebird.scubedao.model;

import java.io.Serializable;

public class ForeignKey implements Serializable, Comparable<ForeignKey> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tableName;
	private String columnName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public int compareTo(ForeignKey o) {
		return this.getTableName().compareTo(o.getTableName());
	}

}
