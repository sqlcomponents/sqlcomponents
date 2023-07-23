package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The type Procedure.
 */
@Getter
@Setter
public class Procedure {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Function name.
     */
    private String functionName;
    /**
     * The Function category.
     */
    private String functionCategory;
    /**
     * The Function schema.
     */
    private String functionSchema;
    /**
     * The Remarks.
     */
    private String remarks;
    /**
     * The Function type.
     */
    private Short functionType;
    /**
     * The Specific name.
     */
    private String specificName;

    /**
     * The Parameters.
     */
    private List<Column> parameters;

    /**
     * The Output.
     */
    private Column output;

}
