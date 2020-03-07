package org.scube.scubedao.model;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Package dbPackage;
	private String serviceName;
	private String daoPackage;
	private List<Method> methods;

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public Package getPackage() {
		return dbPackage;
	}

	public void setPackage(Package dbPackage) {
		this.dbPackage = dbPackage;
	}

	public List<Function> getFunctions() {
		return dbPackage.getFunctions();
	}

	public String getName() {
		return dbPackage.getName();
	}

	public String getRemarks() {
		return dbPackage.getRemarks();
	}

	public void setFunctions(List<Function> functions) {
		dbPackage.setFunctions(functions);
	}

	public void setName(String name) {
		dbPackage.setName(name);
	}

	public void setRemarks(String remarks) {
		dbPackage.setRemarks(remarks);
	}

	@Override
	public String toString() {
		return getName();
	}

}
