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
     * H2 db type.
     */
    H2("H2"),
    /**
     * ORACLE db type.
     */
    ORACLE("ORACLE"),
    /**
     * Mysql db type.
     */
    MYSQL("MYSQL"),
    /**
     * Mariadb db type.
     */
    MARIADB("MARIADB");

    /**
     * The Value.
     */
    private final String value;

    /**
     * Instantiates a new Db type.
     *
     * @param paramValue the value
     */
    DBType(final String paramValue) {
        this.value = paramValue;
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
