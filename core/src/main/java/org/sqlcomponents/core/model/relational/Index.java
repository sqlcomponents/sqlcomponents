package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enums.Order;

/**
 * The type represents an index on a table.
 */
@Getter
@Setter
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

}
