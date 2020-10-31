package org.sqlcomponents.core.model.relational;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Procedure  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String functionName ;
	private String functionCategory;
	private String functionSchema;
	private String remarks ;
	private Short functionType;
	private String specificName;

	private List<Column> parameters ;

	private Column output ;
	

}
