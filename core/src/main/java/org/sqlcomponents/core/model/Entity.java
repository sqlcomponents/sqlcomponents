package org.sqlcomponents.core.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Entity  {

	private Table table;

	private String name;
	private String pluralName;
	private String beanPackage;
	private String daoPackage;
	
	private List<Property> properties;

	public Entity(Table table) {
		setTable(table);
	}

}
