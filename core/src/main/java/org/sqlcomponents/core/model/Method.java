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
     * @param function the function
     */
    public Method(Procedure function) {
        setFunction(function);
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
     * @param functionName the function name
     */
    public void setFunctionName(String functionName) {
        function.setFunctionName(functionName);
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
     * @param remarks the remarks
     */
    public void setRemarks(String remarks) {
        function.setRemarks(remarks);
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * @param inputParameters the input parameters
     */
    public void setInputParameters(List<Property> inputParameters) {
        this.inputParameters = inputParameters;
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
     * @param outputProperty the output property
     */
    public void setOutputProperty(Property outputProperty) {
        this.outputProperty = outputProperty;
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
     * @param function the function
     */
    public void setFunction(Procedure function) {
        this.function = function;
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
     * @param output the output
     */
    public void setOutput(Column output) {
        function.setOutput(output);
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
     * Sets parametes.
     *
     * @param parametes the parametes
     */
    public void setParametes(List<Column> parametes) {
        function.setParameters(parametes);
    }

    @Override
    public String toString() {
        return name;
    }
}
