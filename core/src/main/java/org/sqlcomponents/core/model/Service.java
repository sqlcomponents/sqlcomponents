package org.sqlcomponents.core.model;


import org.sqlcomponents.core.model.relational.Package;
import org.sqlcomponents.core.model.relational.Procedure;

import java.util.List;

public class Service  {

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

	public List<Procedure> getFunctions() {
		return dbPackage.getFunctions();
	}

	public String getName() {
		return dbPackage.getName();
	}

	public String getRemarks() {
		return dbPackage.getRemarks();
	}

	public void setFunctions(List<Procedure> functions) {
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
