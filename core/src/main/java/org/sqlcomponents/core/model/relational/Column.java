package org.sqlcomponents.core.model.relational;

import org.sqlcomponents.core.model.relational.enums.ColumnType;
import org.sqlcomponents.core.model.relational.enums.Flag;

import java.util.SortedSet;

/**
 * The type representing a column in a database table.
 */

public class Column {

    /**
     * The Table.
     */
    private Table table;

    /**
     * The procedure.
     */
    private Procedure procedure;

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
     * Instantiates a new Column of Table.
     *
     * @param aTable the table
     */
    public Column(final Table aTable) {
        this.table = aTable;
    }

    /**
     * Instantiates a new Column for procedure.
     *
     * @param aProcedure the table
     */
    public Column(final Procedure aProcedure) {
        this.procedure = aProcedure;
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

    public Table getTable() {
        return table;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(final String theColumnName) {
        this.columnName = theColumnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(final String theTableName) {
        this.tableName = theTableName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int theSize) {
        this.size = theSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(final int theDecimalDigits) {
        this.decimalDigits = theDecimalDigits;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String theRemarks) {
        this.remarks = theRemarks;
    }

    public Flag getNullable() {
        return nullable;
    }

    public void setNullable(final Flag theNullable) {
        this.nullable = theNullable;
    }

    public Flag getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(final Flag theAutoIncrement) {
        this.autoIncrement = theAutoIncrement;
    }

    public String getUniqueConstraintName() {
        return uniqueConstraintName;
    }

    public void setUniqueConstraintName(final String theUniqueConstraintName) {
        this.uniqueConstraintName = theUniqueConstraintName;
    }

    public int getPrimaryKeyIndex() {
        return primaryKeyIndex;
    }

    public void setPrimaryKeyIndex(final int thePrimaryKeyIndex) {
        this.primaryKeyIndex = thePrimaryKeyIndex;
    }

    public String getTableCategory() {
        return tableCategory;
    }

    public void setTableCategory(final String theTableCategory) {
        this.tableCategory = theTableCategory;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(final String theTableSchema) {
        this.tableSchema = theTableSchema;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String theTypeName) {
        this.typeName = theTypeName;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(final ColumnType theColumnType) {
        this.columnType = theColumnType;
    }

    public int getBufferLength() {
        return bufferLength;
    }

    public void setBufferLength(final int theBufferLength) {
        this.bufferLength = theBufferLength;
    }

    public int getNumberPrecisionRadix() {
        return numberPrecisionRadix;
    }

    public void setNumberPrecisionRadix(final int theNumberPrecisionRadix) {
        this.numberPrecisionRadix = theNumberPrecisionRadix;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(final String theColumnDefinition) {
        this.columnDefinition = theColumnDefinition;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(final int theOrdinalPosition) {
        this.ordinalPosition = theOrdinalPosition;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public void setScopeCatalog(final String theScopeCatalog) {
        this.scopeCatalog = theScopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public void setScopeSchema(final String theScopeSchema) {
        this.scopeSchema = theScopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    public void setScopeTable(final String theScopeTable) {
        this.scopeTable = theScopeTable;
    }

    public String getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(final String theSourceDataType) {
        this.sourceDataType = theSourceDataType;
    }

    public Flag getGeneratedColumn() {
        return generatedColumn;
    }

    public void setGeneratedColumn(final Flag theGeneratedColumn) {
        this.generatedColumn = theGeneratedColumn;
    }

    public SortedSet<Key> getExportedKeys() {
        return exportedKeys;
    }

    public void setExportedKeys(final SortedSet<Key> theExportedKeys) {
        this.exportedKeys = theExportedKeys;
    }

    public Procedure getProcedure() {
        return procedure;
    }
}
