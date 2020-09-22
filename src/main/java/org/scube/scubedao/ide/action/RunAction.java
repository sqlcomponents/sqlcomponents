package org.scube.scubedao.ide.action;

import org.scube.ide.ApplicationManager;
import org.scube.ide.screen.BaseScreen;
import org.scube.scubedao.ide.editor.SCubeDaoEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
