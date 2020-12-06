package org.sqlcomponents.core.model.relational;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Procedure {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String functionName;
    private String functionCategory;
    private String functionSchema;
    private String remarks;
    private Short functionType;
    private String specificName;

    private List<Column> parameters;

    private Column output;


}
