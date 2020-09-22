package com.techatpark.scubebird.core.model;

import java.io.Serializable;
import java.util.List;

public class Function implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String functionName ;
	
	private String remarks ;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private List<Column> parametes ;
	
	private Column output ;


	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public List<Column> getParametes() {
		return parametes;
	}

	public void setParametes(List<Column> parametes) {
		this.parametes = parametes;
	}

	public Column getOutput() {
		return output;
	}

	public void setOutput(Column output) {
		this.output = output;
	}
	
	
}
