package org.sqlcomponents.core.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Database {

	private List<Table> tables ;
	
	private List<Function> functions ;

	private List<Package> packages ;

	private List<String> sequences ;

	
}
