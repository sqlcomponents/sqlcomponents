package com.techatpark.scubebird.scubedao.model;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable {
	public static final String TYPE_VIEW = "VIEW";
	public static final String TYPE_MVIEW = "MVIEW";
	public static final String TYPE_TABLE = "TABLE";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tableName;

	private String sequenceName;

	private String categoryName;

	private String schemaName;

	private String tableType;

	private String remarks;

	private List<Column> columns;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public void addColumn(Column column) {
		this.columns.add(column);
	}

}
