package org.sqlcomponents.core.model.relational.enums;

import org.jetbrains.annotations.Nullable;

public enum Flag
{
    YES("YES"),
    NO("NO"),
    UNKNOWN("");
    private final String value;

    Flag(final String aValue)
    {
	this.value = aValue;
    }

    public static @Nullable Flag value(final String aValue)
    {
	for (Flag bFlag : Flag.values())
	{
	    if (bFlag.value.equals(aValue))
	    {
		return bFlag;
	    }
	}
	return null;
    }
}
