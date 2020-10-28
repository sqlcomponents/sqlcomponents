package org.sqlcomponents.core.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Column implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
    private Boolean isGeneratedColumn;

    private List<ForeignKey> foreignKeys;

    // Null if nothing
    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        if (foreignKeys != null) {
            Collections.sort(foreignKeys);
        }
        this.foreignKeys = foreignKeys;
    }

    public String getTableCategory() {
        return tableCategory;
    }

    public void setTableCategory(String tableCategory) {
        this.tableCategory = tableCategory;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getBufferLength() {
        return bufferLength;
    }

    public void setBufferLength(int bufferLength) {
        this.bufferLength = bufferLength;
    }

    public int getNumberPrecisionRadix() {
        return numberPrecisionRadix;
    }

    public void setNumberPrecisionRadix(int numberPrecisionRadix) {
        this.numberPrecisionRadix = numberPrecisionRadix;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public void setScopeCatalog(String scopeCatalog) {
        this.scopeCatalog = scopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public void setScopeSchema(String scopeSchema) {
        this.scopeSchema = scopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    public void setScopeTable(String scopeTable) {
        this.scopeTable = scopeTable;
    }

    public String getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(String sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public Boolean getGeneratedColumn() {
        return isGeneratedColumn;
    }

    public void setGeneratedColumn(Boolean generatedColumn) {
        isGeneratedColumn = generatedColumn;
    }


    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(final boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(String sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public String getUniqueConstraintName() {
        return uniqueConstraintName;
    }

    public void setUniqueConstraintName(String uniqueConstraintName) {
        this.uniqueConstraintName = uniqueConstraintName;
    }

    public int getPrimaryKeyIndex() {
        return primaryKeyIndex;
    }

    public void setPrimaryKeyIndex(int primaryKeyIndex) {
        this.primaryKeyIndex = primaryKeyIndex;
    }

}
