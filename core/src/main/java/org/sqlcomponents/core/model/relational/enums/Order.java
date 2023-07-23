package org.sqlcomponents.core.model.relational.enums;

/**
 * The enum Order.
 */
public enum Order {
    /**
     * Asc order.
     */
    ASC("A"),
    /**
     * Desc order.
     */
    DESC("D");

    /**
     * The Value.
     */
    private final String value;

    /**
     * Instantiates a new Order.
     *
     * @param value the value
     */
    Order(final String value) {
        this.value = value;
    }

    /**
     * Value order.
     *
     * @param value the value
     * @return the order
     */
    public static Order value(final String value) {

        for (Order flag : Order.values()) {
            if (flag.value.equals(value)) {
                return flag;
            }
        }
        return null;
    }
}
