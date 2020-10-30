package org.sqlcomponents.core.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function  {

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
