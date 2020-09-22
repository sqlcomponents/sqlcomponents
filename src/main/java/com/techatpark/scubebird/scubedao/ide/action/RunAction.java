package com.techatpark.scubebird.scubedao.ide.action;

import com.techatpark.scubebird.ide.ApplicationManager;
import com.techatpark.scubebird.ide.screen.BaseScreen;
import com.techatpark.scubebird.scubedao.ide.editor.SCubeDaoEditor;

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
