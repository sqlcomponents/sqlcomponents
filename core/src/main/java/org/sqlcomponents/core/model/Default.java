package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Default {

    private boolean onInsert;
    private boolean onUpdate;
    private String columnName;
    private String defaultValue;

}
