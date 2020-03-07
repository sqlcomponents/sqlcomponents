package org.scube.ide.model;

import java.io.Serializable;

public class CrawlerConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String driverName;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	private String name;
	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		CrawlerConfig config = (CrawlerConfig) obj;
		// TODO Auto-generated method stub
		return config != null && driverName.equals(config.getDriverName())
				&& className.equals(config.getClassName());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
