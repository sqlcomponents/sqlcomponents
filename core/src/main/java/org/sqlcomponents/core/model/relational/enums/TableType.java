package org.sqlcomponents.core.model.relational.enums;

/**
 * The enum Table type.
 */
public enum TableType {
    /**
     * Table table type.
     */
    TABLE("TABLE"),
    /**
     * The Foreign table.
     */
    FOREIGN_TABLE("FOREIGN TABLE"),
    /**
     * The Partitioned table.
     */
    PARTITIONED_TABLE("PARTITIONED TABLE"),
    /**
     * The System table.
     */
    SYSTEM_TABLE("SYSTEM TABLE"),
    /**
     * The System toast table.
     */
    SYSTEM_TOAST_TABLE("SYSTEM TOAST TABLE"),
    /**
     * The Temporary table.
     */
    TEMPORARY_TABLE("TEMPORARY TABLE"),
    /**
     * View table type.
     */
    VIEW("VIEW"),
    /**
     * The Materialized view.
     */
    MATERIALIZED_VIEW("MATERIALIZED VIEW"),
    /**
     * The System view.
     */
    SYSTEM_VIEW("SYSTEM VIEW"),
    /**
     * The Temporary view.
     */
    TEMPORARY_VIEW("TEMPORARY VIEW"),
    /**
     * Sequence table type.
     */
    SEQUENCE("SEQUENCE"),
    /**
     * The Temporary sequence.
     */
    TEMPORARY_SEQUENCE("TEMPORARY SEQUENCE"),
    /**
     * Index table type.
     */
    INDEX("INDEX"),
    /**
     * The System index.
     */
    SYSTEM_INDEX("SYSTEM INDEX"),
    /**
     * The Temporary index.
     */
    TEMPORARY_INDEX("TEMPORARY INDEX"),
    /**
     * The System toast index.
     */
    SYSTEM_TOAST_INDEX("SYSTEM TOAST INDEX"),
    /**
     * The Global temporary.
     */
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    /**
     * The Local temporary.
     */
    LOCAL_TEMPORARY("LOCAL TEMPORARY"),
    /**
     * The Partitioned index.
     */
    PARTITIONED_INDEX("PARTITIONED INDEX"),
    /**
     * Type table type.
     */
    TYPE("TYPE"),
    /**
     * Alias table type.
     */
    ALIAS("ALIAS"),
    /**
     * Synonym table type.
     */
    SYNONYM("SYNONYM");

    private final String value;

    /**
     * Instantiates a new Table type.
     *
     * @param paramValue the param value
     */
    TableType(final String paramValue) {
        this.value = paramValue;
    }

    /**
     * Value table type.
     *
     * @param value the value
     * @return the table type
     */
    public static TableType value(final String value) {

        for (TableType tableType : TableType.values()) {
            if (tableType.value.equals(value)) {
                return tableType;
            }
        }
        return null;
    }
}
