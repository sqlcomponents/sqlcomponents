package org.scube.scubedao.model;

import java.io.Serializable;

/**
 * @author photoninfotech
 * 
 */
public class Default implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean onInsert;
	private boolean onUpdate;
	private String columnName;
	private String defaultValue;

	public boolean isOnInsert() {
		return onInsert;
	}

	public void setOnInsert(boolean onInsert) {
		this.onInsert = onInsert;
	}

	public boolean isOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(boolean onUpdate) {
		this.onUpdate = onUpdate;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
