package org.sqlcomponents.core.model.relational.enums;

public enum Order {
    ASC("A"), DESC("D");

    private final String value;

    Order(final String value) {
        this.value = value;
    }

    public static Order value(final String value) {

        for (Order flag : Order.values()) {
            if (flag.value.equals(value)) {
                return flag;
            }
        }
        return null;
    }
}
