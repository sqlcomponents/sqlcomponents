package org.sqlcomponents.core.model.relational.enums;

import org.jetbrains.annotations.Nullable;

/**
 * The enum Flag.
 */
public enum Flag {
    /**
     * Yes flag.
     */
    YES("YES"),
    /**
     * No flag.
     */
    NO("NO"),
    /**
     * Unknown flag.
     */
    UNKNOWN("");

    /**
     * The Value.
     */
    private final String value;

    /**
     * Instantiates a new Flag.
     *
     * @param aValue the a value
     */
    Flag(final String aValue) {
        this.value = aValue;
    }

    /**
     * Value flag.
     *
     * @param aValue the a value
     * @return the flag
     */
    public static @Nullable Flag value(final String aValue) {
        for (Flag bFlag : Flag.values()) {
            if (bFlag.value.equalsIgnoreCase(aValue)) {
                return bFlag;
            }
        }
        return null;
    }
}
