package org.sqlcomponents.core.model.relational.enumeration;

public enum DatabaseType {
    POSTGRES("POSTGRES"),
    MYSQL("MYSQL"),
    MARIADB("MARIADB");

    private final String value;

    DatabaseType(final String value) {
        this.value = value;
    }

    public static DatabaseType value(final String value) {

        for (DatabaseType databaseType :
                DatabaseType.values()) {
            if (databaseType.value.equals(value)) {
                return databaseType;
            }
        }
        return null;
    }
}
