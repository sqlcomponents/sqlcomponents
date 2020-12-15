package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enumeration.Order;

@Getter
@Setter
public class Index {

    private boolean unique;
    private String indexQualifier;
    private String indexName;
    private short type;
    private short ordinalPosition;
    private String columnName;
    private Order order;
    private int cardinality;
    private int pages;
    private String filterCondition;

}
