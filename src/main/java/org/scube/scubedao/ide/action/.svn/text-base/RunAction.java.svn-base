package org.scube.scubedao.ide.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.scube.ide.ApplicationManager;
import org.scube.ide.screen.BaseScreen;
import org.scube.scubedao.ide.editor.SCubeDaoEditor;

public class RunAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		BaseScreen curentScreen = ApplicationManager.getApplicationManager()
		.getCurrentScreen();
		if( curentScreen instanceof SCubeDaoEditor ) {
			((SCubeDaoEditor)curentScreen).writeCode() ;
		}
		
	}

}
