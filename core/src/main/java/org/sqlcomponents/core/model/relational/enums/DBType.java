package org.sqlcomponents.core.model.relational.enums;

/**
 * The enum Db type.
 */
public enum DBType {
    /**
     * Postgres db type.
     */
    POSTGRES("POSTGRES"),
    /**
     * Mysql db type.
     */
    MYSQL("MYSQL"),
    /**
     * Mariadb db type.
     */
    MARIADB("MARIADB");

    private final String value;

    DBType(final String value) {
        this.value = value;
    }

    /**
     * Value db type.
     *
     * @param aValue the a value
     * @return the db type
     */
    public static DBType value(final String aValue) {
        for (DBType lDBType : DBType.values()) {
            if (lDBType.value.equals(aValue)) {
                return lDBType;
            }
        }
        return null;
    }
}
