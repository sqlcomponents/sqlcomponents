package org.sqlcomponents.core.model.enumeration;

public enum Flag {
    YES("YES"),
    NO("NO"),
    UNKNOWN("");
    private final String value;

    Flag(final String value) {
        this.value = value;
    }

    public static Flag value(final String value) {

        for (Flag flag :
                Flag.values()) {
            if (flag.value.equals(value)) {
                return flag;
            }
        }
        return null;
    }

}
