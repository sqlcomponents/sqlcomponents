package org.sqlcomponents.core.model.relational;

/**
 * The type Key.
 */
public class Key implements Comparable<Key> {

    /**
     * The Table name.
     */
    private String tableName;
    /**
     * The Column name.
     */
    private String columnName;

    /**
     * Gets table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets table name.
     *
     * @param paramTableName the table name
     */
    public void setTableName(final String paramTableName) {
        this.tableName = paramTableName;
    }

    /**
     * Gets column name.
     *
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets column name.
     *
     * @param paramColumnName the column name
     */
    public void setColumnName(final String paramColumnName) {
        this.columnName = paramColumnName;
    }

    /**
     * Copareby table name.
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(final Key o) {
        return this.getTableName().compareTo(o.getTableName());
    }

}
