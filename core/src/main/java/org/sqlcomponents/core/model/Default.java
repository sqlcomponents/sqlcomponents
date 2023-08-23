package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Default.
 */
@Getter
@Setter
public class Default {
    /**
     * The On insert.
     */
    private boolean onInsert;
    /**
     * The On update.
     */
    private boolean onUpdate;
    /**
     * The Column name.
     */
    private String columnName;
    /**
     * The Default value.
     */
    private String defaultValue;
}
