package org.sqlcomponents.core.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String functionName ;
	
	private String remarks ;

	private List<Column> parametes ;

	private Column output ;
	

}
