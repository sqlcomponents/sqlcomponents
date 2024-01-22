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
    private final boolean nonUnique;
    /**
     * The Index qualifier.
     */
    private final String indexQualifier;
    /**
     * The Index name.
     */
    private final String indexName;
    /**
     * The Type.
     */
    private final short type;
    /**
     * The Ordinal position.
     */
    private final short ordinalPosition;
    /**
     * The Column name.
     */
    private final String columnName;
    /**
     * The Order.
     */
    private final Order order;
    /**
     * The Cardinality.
     */
    private final int cardinality;
    /**
     * The Pages.
     */
    private final int pages;
    /**
     * The Filter condition.
     */
    private final String filterCondition;

    /**
     * Instantiates a new Index.
     *
     * @param paramTable      the table
     * @param nonUnique
     * @param indexQualifier
     * @param indexName
     * @param type
     * @param ordinalPosition
     * @param columnName
     * @param order
     * @param cardinality
     * @param pages
     * @param filterCondition
     */
    public Index(final Table paramTable, boolean nonUnique, String indexQualifier, String indexName, short type, short ordinalPosition, String columnName, Order order, int cardinality, int pages, String filterCondition) {
        this.table = paramTable;
        this.nonUnique = nonUnique;
        this.indexQualifier = indexQualifier;
        this.indexName = indexName;
        this.type = type;
        this.ordinalPosition = ordinalPosition;
        this.columnName = columnName;
        this.order = order;
        this.cardinality = cardinality;
        this.pages = pages;
        this.filterCondition = filterCondition;
    }

    public Table getTable() {
        return table;
    }

    public boolean isNonUnique() {
        return nonUnique;
    }


    public String getIndexQualifier() {
        return indexQualifier;
    }


    public String getIndexName() {
        return indexName;
    }


    public short getType() {
        return type;
    }


    public short getOrdinalPosition() {
        return ordinalPosition;
    }


    public String getColumnName() {
        return columnName;
    }


    public Order getOrder() {
        return order;
    }


    public int getCardinality() {
        return cardinality;
    }


    public int getPages() {
        return pages;
    }


    public String getFilterCondition() {
        return filterCondition;
    }

  }
