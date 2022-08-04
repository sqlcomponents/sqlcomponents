package org.sqlcomponents.core.model;

import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Procedure;

import java.util.List;

public class Method {
    private String name;
    private List<Property> inputParameters;
    private Property outputProperty;
    private Procedure function;

    public Method(Procedure function) {
        setFunction(function);
    }

    public String getFunctionName() {
        return function.getFunctionName();
    }

    public void setFunctionName(String functionName) {
        function.setFunctionName(functionName);
    }

    public String getRemarks() {
        return function.getRemarks();
    }

    public void setRemarks(String remarks) {
        function.setRemarks(remarks);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<Property> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public Property getOutputProperty() {
        return outputProperty;
    }

    public void setOutputProperty(Property outputProperty) {
        this.outputProperty = outputProperty;
    }

    public Procedure getFunction() {
        return function;
    }

    public void setFunction(Procedure function) {
        this.function = function;
    }

    public Column getOutput() {
        return function.getOutput();
    }

    public void setOutput(Column output) {
        function.setOutput(output);
    }

    public List<Column> getParameters() {
        return function.getParameters();
    }

    public void setParametes(List<Column> parametes) {
        function.setParameters(parametes);
    }

    @Override
    public String toString() {
        return name;
    }
}
