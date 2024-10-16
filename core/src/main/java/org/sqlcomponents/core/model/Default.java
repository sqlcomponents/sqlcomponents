package org.sqlcomponents.core.model;



/**
 * The type Default.
 */

public class Default {
    /**
     * The On insert.
     */
    private boolean onInsert;
    /**
     * The On update.
     */
    private boolean onUpdate;
    /**
     * The Column name.
     */
    private String columnName;
    /**
     * The Default value.
     */
    private String defaultValue;

    public boolean isOnInsert() {
        return onInsert;
    }

    public void setOnInsert(final boolean theOnInsert) {
        this.onInsert = theOnInsert;
    }

    public boolean isOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(final boolean theOnUpdate) {
        this.onUpdate = theOnUpdate;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(final String theColumnName) {
        this.columnName = theColumnName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(final String theDefaultValue) {
        this.defaultValue = theDefaultValue;
    }
}
