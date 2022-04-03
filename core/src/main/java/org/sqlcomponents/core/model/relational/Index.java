package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enums.Order;

@Getter
@Setter
public class Index
{

    private final Table table;

    private boolean nonUnique;
    private String indexQualifier;
    private String indexName;
    private short type;
    private short ordinalPosition;
    private String columnName;
    private Order order;
    private int cardinality;
    private int pages;
    private String filterCondition;

    public Index(final Table table)
    {
	this.table = table;
    }

}
