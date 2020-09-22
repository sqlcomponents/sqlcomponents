package com.techatpark.scubebird.scubedao.model;

import java.io.Serializable;
import java.util.List;

public class Method implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getFunctionName() {
		return function.getFunctionName();
	}

	public void setFunctionName(String functionName) {
		function.setFunctionName(functionName);
	}

	private String name;
	

	private List<Property> inputParameters;

	private Property outputProperty;

	private Function function;
	
	public String getRemarks() {
		return function.getRemarks();
	}

	public void setRemarks(String remarks) {
		function.setRemarks(remarks);
	}

	public Method(Function function) {
		setFunction(function) ;
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

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Column getOutput() {
		return function.getOutput();
	}



	public List<Column> getParametes() {
		return function.getParametes();
	}

	public void setOutput(Column output) {
		function.setOutput(output);
	}



	public void setParametes(List<Column> parametes) {
		function.setParametes(parametes);
	}
	
	@Override
	public String toString() {
		return name;
	}
	

}
