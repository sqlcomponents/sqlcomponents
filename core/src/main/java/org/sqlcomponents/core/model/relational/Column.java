package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enums.ColumnType;
import org.sqlcomponents.core.model.relational.enums.Flag;

import java.util.SortedSet;

/**
 * The type representing a column in a database table.
 */
@Setter
@Getter
public class Column {

    /**
     * The Table.
     */
    private final Table table;
    /**
     * The Column name.
     */
    private String columnName;
    /**
     * The Table name.
     */
    private String tableName;
    /**
     * The Size.
     */
    private int size;
    /**
     * The Decimal digits.
     */
    private int decimalDigits;
    /**
     * The Remarks.
     */
    private String remarks;
    /**
     * The Nullable.
     */
    private Flag nullable;
    /**
     * The Auto increment.
     */
    private Flag autoIncrement;
    /**
     * The Unique constraint name.
     */
    private String uniqueConstraintName;
    /**
     * The Primary key index.
     */
    private int primaryKeyIndex;
    /**
     * The Table category.
     */
    private String tableCategory;
    /**
     * The Table schema.
     */
    private String tableSchema;
    /**
     * The Type name.
     */
    private String typeName;
    /**
     * The Column type.
     */
    private ColumnType columnType;
    /**
     * The Buffer length.
     */
    private int bufferLength;
    /**
     * The Number precision radix.
     */
    private int numberPrecisionRadix;
    /**
     * The Column definition.
     */
    private String columnDefinition;
    /**
     * The Ordinal position.
     */
    private int ordinalPosition;
    /**
     * The Scope catalog.
     */
    private String scopeCatalog;
    /**
     * The Scope schema.
     */
    private String scopeSchema;
    /**
     * The Scope table.
     */
    private String scopeTable;
    /**
     * The Source data type.
     */
    private String sourceDataType;
    /**
     * The Generated column.
     */
    private Flag generatedColumn;
    /**
     * The Exported keys.
     */
    private SortedSet<Key> exportedKeys;

    /**
     * Instantiates a new Column.
     *
     * @param aTable the table
     */
    public Column(final Table aTable) {
        this.table = aTable;
    }

    /**
     * Gets escaped name.
     *
     * @return the escaped name
     */
    public String getEscapedName() {
        return this.table.getDatabase().escapedName(this.getColumnName());
    }

    /**
     * Is insertable boolean.
     *
     * @return the boolean
     */
    public boolean isInsertable() {
        return autoIncrement != Flag.YES && generatedColumn != Flag.YES;
    }

    /**
     * Is primary key boolean.
     *
     * @return the boolean
     */
    public boolean isPrimaryKey() {
        return primaryKeyIndex != 0;
    }
}
