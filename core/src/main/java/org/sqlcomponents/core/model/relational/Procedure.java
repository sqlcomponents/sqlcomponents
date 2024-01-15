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

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(final String theFunctionName) {
        this.functionName = theFunctionName;
    }

    public String getFunctionCategory() {
        return functionCategory;
    }

    public void setFunctionCategory(final String theFunctionCategory) {
        this.functionCategory = theFunctionCategory;
    }

    public String getFunctionSchema() {
        return functionSchema;
    }

    public void setFunctionSchema(final String theFunctionSchema) {
        this.functionSchema = theFunctionSchema;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String theRemarks) {
        this.remarks = theRemarks;
    }

    public Short getFunctionType() {
        return functionType;
    }

    public void setFunctionType(final Short theFunctionType) {
        this.functionType = theFunctionType;
    }

    public String getSpecificName() {
        return specificName;
    }

    public void setSpecificName(final String theSpecificName) {
        this.specificName = theSpecificName;
    }

    public List<Column> getParameters() {
        return parameters;
    }

    public void setParameters(final List<Column> theParameters) {
        this.parameters = theParameters;
    }

    public Column getOutput() {
        return output;
    }

    public void setOutput(final Column theOutput) {
        this.output = theOutput;
    }
}
