package org.sqlcomponents.core.model.relational.enums;

public enum TableType {
    TABLE("TABLE"), FOREIGN_TABLE("FOREIGN TABLE"), PARTITIONED_TABLE("PARTITIONED TABLE"),
    SYSTEM_TABLE("SYSTEM TABLE"), SYSTEM_TOAST_TABLE("SYSTEM TOAST TABLE"), TEMPORARY_TABLE("TEMPORARY TABLE"),
    VIEW("VIEW"), MATERIALIZED_VIEW("MATERIALIZED VIEW"), SYSTEM_VIEW("SYSTEM VIEW"), TEMPORARY_VIEW("TEMPORARY VIEW"),
    SEQUENCE("SEQUENCE"), TEMPORARY_SEQUENCE("TEMPORARY SEQUENCE"), INDEX("INDEX"), SYSTEM_INDEX("SYSTEM INDEX"),
    TEMPORARY_INDEX("TEMPORARY INDEX"), SYSTEM_TOAST_INDEX("SYSTEM TOAST INDEX"), GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    LOCAL_TEMPORARY("LOCAL TEMPORARY"), PARTITIONED_INDEX("PARTITIONED INDEX"),
    TYPE("TYPE"), ALIAS("ALIAS"), SYNONYM("SYNONYM");

    private final String value;

    TableType(final String value) {
        this.value = value;
    }

    public static TableType value(final String value) {

        for (TableType tableType : TableType.values()) {
            if (tableType.value.equals(value)) {
                return tableType;
            }
        }
        return null;
    }
}
