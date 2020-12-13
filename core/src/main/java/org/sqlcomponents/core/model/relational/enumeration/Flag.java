package org.sqlcomponents.core.model.relational.enumeration;

public enum Flag {
    YES("YES"),
    NO("NO"),
    UNKNOWN("");
    private final String value;

    Flag(final String value) {
        this.value = value;
    }

}
