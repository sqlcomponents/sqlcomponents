package org.sqlcomponents.core.model;

import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Procedure;

import java.util.List;

/**
 * The type Method.
 */
public class Method {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Input parameters.
     */
    private List<Property> inputParameters;
    /**
     * The Output property.
     */
    private Property outputProperty;
    /**
     * The Function.
     */
    private Procedure function;

    /**
     * Instantiates a new Method.
     *
     * @param paramFunction the function
     */
    public Method(final Procedure paramFunction) {

    }

    /**
     * Gets function name.
     *
     * @return the function name
     */
    public String getFunctionName() {
        return function.getFunctionName();
    }

    /**
     * Sets function name.
     *
     * @param paramFunctionName the function name
     */


    /**
     * Gets remarks.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return function.getRemarks();
    }

    /**
     * Sets remarks.
     *
     * @param paramRemarks the remarks
     */

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

    /**
     * Gets input parameters.
     *
     * @return the input parameters
     */
    public List<Property> getInputParameters() {
        return inputParameters;
    }

    /**
     * Sets input parameters.
     *
     * @param paramInputParameters the input parameters
     */


    /**
     * Gets output property.
     *
     * @return the output property
     */
    public Property getOutputProperty() {
        return outputProperty;
    }

    /**
     * Sets output property.
     *
     * @param paramOutputProperty the output property
     */

    /**
     * Gets function.
     *
     * @return the function
     */
    public Procedure getFunction() {
        return function;
    }

    /**
     * Sets function.
     *
     * @param paramFunction the function
     */

    /**
     * Gets output.
     *
     * @return the output
     */
    public Column getOutput() {
        return function.getOutput();
    }

    /**
     * Sets output.
     *
     * @param paramOutput the output
     */

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public List<Column> getParameters() {
        return function.getParameters();
    }

    /**
     * Sets parameters.
     *
     * @param paramParameters the parameters
     */

    /**
     * Should return the name of the method.
     * @return
     */
    @Override
    public String toString() {
        return name;
    }
}
