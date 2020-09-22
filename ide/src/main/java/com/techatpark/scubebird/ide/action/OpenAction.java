package com.techatpark.scubebird.ide.action;

import com.techatpark.scubebird.ide.ApplicationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

final class OpenAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		ApplicationManager.getApplicationManager().openFile("dao",
				"Dao Project","com.techatpark.scubebird.ide.scubedao.ide.editor.SCubeDaoEditor");
	}

}
