package org.sqlcomponents.core.model.relational;

import java.util.List;

/**
 * The type representing a unique constraint in a database.
 */
public class UniqueConstraint {

    /**
     * The Name.
     */
    private String name;
    /**
     * The Columns.
     */
    private List<Column> columns;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param paramName the name
     */
    public void setName(final String paramName) {
        this.name = paramName;
    }

    /**
     * Gets columns.
     *
     * @return the columns
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * Sets columns.
     *
     * @param paramColumns the columns
     */
    public void setColumns(final List<Column> paramColumns) {
        this.columns = paramColumns;
    }
}
