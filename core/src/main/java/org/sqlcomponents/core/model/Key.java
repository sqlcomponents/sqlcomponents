package org.sqlcomponents.core.model;

public class Key implements Comparable<Key> {

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
	public int compareTo(Key o) {
		return this.getTableName().compareTo(o.getTableName());
	}

}
