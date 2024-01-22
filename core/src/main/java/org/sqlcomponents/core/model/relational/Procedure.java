package org.sqlcomponents.core.model.relational;



import java.util.List;

/**
 * The type Procedure.
 */

public class Procedure {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Function name.
     */
    private final String functionName;
    /**
     * The Function category.
     */
    private final String functionCategory;
    /**
     * The Function schema.
     */
    private final String functionSchema;
    /**
     * The Remarks.
     */
    private final String remarks;
    /**
     * The Function type.
     */
    private final Short functionType;
    /**
     * The Specific name.
     */
    private final String specificName;

    /**
     * The Parameters.
     */
    private final List<Column> parameters;

    /**
     * The Output.
     */
    private final Column output;

    public Procedure(String functionName, String functionCategory, String functionSchema, String remarks, Short functionType, String specificName, List<Column> parameters, Column output) {
        this.functionName = functionName;
        this.functionCategory = functionCategory;
        this.functionSchema = functionSchema;
        this.remarks = remarks;
        this.functionType = functionType;
        this.specificName = specificName;
        this.parameters = parameters;
        this.output = output;
    }

    public String getFunctionName() {
        return functionName;
    }


    public String getFunctionCategory() {
        return functionCategory;
    }


    public String getFunctionSchema() {
        return functionSchema;
    }


    public String getRemarks() {
        return remarks;
    }


    public Short getFunctionType() {
        return functionType;
    }


    public String getSpecificName() {
        return specificName;
    }


    public List<Column> getParameters() {
        return parameters;
    }


    public Column getOutput() {
        return output;
    }


}
