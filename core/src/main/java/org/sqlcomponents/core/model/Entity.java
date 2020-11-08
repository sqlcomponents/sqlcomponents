package org.sqlcomponents.core.model;


import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.Table;

import java.util.List;

@Getter
@Setter
public class Entity  {

	private Table table;

	private String name;
	private String pluralName;
	private String beanPackage;
	private String daoPackage;

	private ORM orm;
	
	private List<Property> properties;

	public Entity(ORM orm, Table table) {
		setOrm(orm); ;
		setTable(table);
	}

}
