package org.sqlcomponents.core.model;


import java.util.List;

public class Package  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String remarks;
	private List<Function> functions;

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
