package org.sqlcomponents.core.model.relational;


import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enumeration.Flag;

import java.sql.JDBCType;
import java.util.SortedSet;

@Setter
@Getter
public class Column  {

    private String columnName;
    private String tableName;
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
    private JDBCType jdbcType;
    private int bufferLength;
    private int numberPrecisionRadix;
    private String columnDefinition;
    private int ordinalPosition;
    private String scopeCatalog;
    private String scopeSchema;
    private String scopeTable;
    private String sourceDataType;
    private Flag generatedColumn;

    private SortedSet<Key> exportedKeys;

    private final Table table;

    public Column(final Table table) {
        this.table = table;
    }


    public String getEscapedName() {
        return this.table.getDatabase().escapedName(this.getColumnName());
    }

    public boolean isInsertable() {
        return !isAutoIncrement && generatedColumn != Flag.YES;
    }
}
