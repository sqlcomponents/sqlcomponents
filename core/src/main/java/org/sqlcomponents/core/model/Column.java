package org.sqlcomponents.core.model;


import lombok.Getter;
import lombok.Setter;

import java.util.SortedSet;

@Setter
@Getter
public class Column  {

    private String columnName;
    private String tableName;
    private String sqlDataType;
    private int size;
    private int decimalDigits;
    private String remarks;
    private boolean isNullable;
    private boolean isAutoIncrement;
    private String uniqueConstraintName;
    private int primaryKeyIndex;

    private String tableCategory;
    private String tableSchema;
    private String typeName;
    private int bufferLength;
    private int numberPrecisionRadix;
    private String columnDefinition;
    private int ordinalPosition;
    private String scopeCatalog;
    private String scopeSchema;
    private String scopeTable;
    private String sourceDataType;
    private Boolean generatedColumn;

    private SortedSet<Key> exportedKeys;


}
