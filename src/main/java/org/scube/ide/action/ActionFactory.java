package org.scube.ide.action;

import javax.swing.*;

public final class ActionFactory {
	
	private static OpenAction openAction;
	private static NewAction newAction;
	private static PropertyAction propertyAction ;
	private static SaveAction saveAction ;
	private static AboutAction aboutAction ;

	public static AboutAction getAboutAction() {
		if( aboutAction == null) {
			aboutAction = new AboutAction() ;
		}
		return aboutAction;
	}

	public static Action getOpenAction() {		
		if( openAction == null ) {
			openAction = new OpenAction() ;
		}
		return openAction;
	}
	
	public static Action getNewAction() {
		if( newAction == null ) {
			newAction = new NewAction() ;
		}
		return newAction;
	}

	public static Action getSaveAction() {
		if( saveAction == null ) {
			saveAction = new SaveAction() ;
		}
		return saveAction;
	}
	public static Action getPropertyAction() {
		if( propertyAction == null ) {
			propertyAction = new PropertyAction() ;
		}
		return propertyAction;
	}

}
