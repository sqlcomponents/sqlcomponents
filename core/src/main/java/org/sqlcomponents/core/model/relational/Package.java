package org.sqlcomponents.core.model.relational;

import java.util.List;

/**
 * The type Package.
 */
public class Package {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Name.
     */
    private String name;
    /**
     * The Remarks.
     */
    private String remarks;
    /**
     * The Functions.
     */
    private List<Procedure> functions;

    /**
     * Gets functions.
     *
     * @return the functions
     */
    public List<Procedure> getFunctions() {
        return functions;
    }

    /**
     * Sets functions.
     *
     * @param paramFunctions the functions
     */
    public void setFunctions(final List<Procedure> paramFunctions) {
        this.functions = paramFunctions;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param paramName the name
     */
    public void setName(final String paramName) {
        this.name = paramName;
    }

    /**
     * Gets remarks.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets remarks.
     *
     * @param paramRemarks the remarks
     */
    public void setRemarks(final String paramRemarks) {
        this.remarks = paramRemarks;
    }

}
