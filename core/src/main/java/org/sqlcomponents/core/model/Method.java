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
        setFunction(paramFunction);
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
    public void setFunctionName(final String paramFunctionName) {
        function.setFunctionName(paramFunctionName);
    }

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
    public void setRemarks(final String paramRemarks) {
        function.setRemarks(paramRemarks);
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
    public void setInputParameters(final List<Property> paramInputParameters) {
        this.inputParameters = paramInputParameters;
    }

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
    public void setOutputProperty(final Property paramOutputProperty) {
        this.outputProperty = paramOutputProperty;
    }

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
    public void setFunction(final Procedure paramFunction) {
        this.function = paramFunction;
    }

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
    public void setOutput(final Column paramOutput) {
        function.setOutput(paramOutput);
    }

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
    public void setParametes(final List<Column> paramParameters) {
        function.setParameters(paramParameters);
    }

    @Override
    public String toString() {
        return name;
    }
}
