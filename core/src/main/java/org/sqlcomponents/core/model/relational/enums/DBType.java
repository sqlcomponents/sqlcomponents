package org.sqlcomponents.core.model.relational.enums;

public enum DBType {
    POSTGRES("POSTGRES"), MYSQL("MYSQL"), MARIADB("MARIADB");

    private final String value;

    DBType(final String value) {
        this.value = value;
    }

    public static DBType value(final String aValue) {
        for (DBType lDBType : DBType.values()) {
            if (lDBType.value.equals(aValue)) {
                return lDBType;
            }
        }
        return null;
    }
}
