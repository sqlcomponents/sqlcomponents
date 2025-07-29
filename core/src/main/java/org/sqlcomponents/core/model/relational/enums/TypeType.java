package org.sqlcomponents.core.model.relational.enums;

/**
 * Type's type.
 */
public enum TypeType {
    /**
     * base type.
     */
    b("b"),
    /**
     * Enum type.
     */
    c("c"),
    /**
     * Domain type.
     */
    d("d"),
    /**
     * Enum type.
     */
    e("e"),
    /**
     * Multi range type.
     */
    m("m"),
    /**
     * Pseudo type.
     */
    p("p"),
    /**
     * Range type.
     */
    r("r");

    /**
     * The Value.
     */
    private final String value;

    /**
     * Instantiates a new type type.
     *
     * @param paramValue the value
     */
    TypeType(final String paramValue) {
        this.value = paramValue;
    }
    /**
     * Value type type.
     *
     * @param aValue the a value
     * @return the db type
     */
    public static TypeType value(final String aValue) {
        for (TypeType typeType : TypeType.values()) {
            if (typeType.value.equals(aValue)) {
                return typeType;
            }
        }
        return null;
    }
}
