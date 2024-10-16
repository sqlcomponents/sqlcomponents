package org.sqlcomponents.core.model.relational;

import org.sqlcomponents.core.model.relational.enums.Order;

/**
 * The type represents an index on a table.
 */

public class Index {

    /**
     * The Table.
     */
    private final Table table;

    /**
     * The Non unique.
     */
    private boolean nonUnique;
    /**
     * The Index qualifier.
     */
    private String indexQualifier;
    /**
     * The Index name.
     */
    private String indexName;
    /**
     * The Type.
     */
    private short type;
    /**
     * The Ordinal position.
     */
    private short ordinalPosition;
    /**
     * The Column name.
     */
    private String columnName;
    /**
     * The Order.
     */
    private Order order;
    /**
     * The Cardinality.
     */
    private int cardinality;
    /**
     * The Pages.
     */
    private int pages;
    /**
     * The Filter condition.
     */
    private String filterCondition;

    /**
     * Instantiates a new Index.
     *
     * @param paramTable the table
     */
    public Index(final Table paramTable) {
        this.table = paramTable;
    }

    public Table getTable() {
        return table;
    }

    public boolean isNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(final boolean theNonUnique) {
        this.nonUnique = theNonUnique;
    }

    public String getIndexQualifier() {
        return indexQualifier;
    }

    public void setIndexQualifier(final String theIndexQualifier) {
        this.indexQualifier = theIndexQualifier;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(final String theIndexName) {
        this.indexName = theIndexName;
    }

    public short getType() {
        return type;
    }

    public void setType(final short theType) {
        this.type = theType;
    }

    public short getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(final short theOrdinalPosition) {
        this.ordinalPosition = theOrdinalPosition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(final String theColumnName) {
        this.columnName = theColumnName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order theOrder) {
        this.order = theOrder;
    }

    public int getCardinality() {
        return cardinality;
    }

    public void setCardinality(final int theCardinality) {
        this.cardinality = theCardinality;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(final int thePages) {
        this.pages = thePages;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(final String theFilterCondition) {
        this.filterCondition = theFilterCondition;
    }
}
