package org.sqlcomponents.core.model.relational.enumeration;

public enum TableType {

    TABLE("TABLE"),
    SYSTEM_TABLE("SYSTEM TABLE"),
    VIEW("VIEW"),
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    LOCAL_TEMPORARY("LOCAL TEMPORARY"),
    ALIAS("ALIAS"),
    SYNONYM("SYNONYM");

    private final String value;

    TableType(final String value) {
        this.value = value;
    }

    public static TableType value(final String value) {

        for (TableType tableType :
                TableType.values()) {
            if (tableType.value.equals(value)) {
                return tableType;
            }
        }
        return null;
    }
}
