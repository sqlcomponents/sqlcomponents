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
	
	private String remarks ;

	private List<Column> parametes ;

	private Column output ;
	

}
