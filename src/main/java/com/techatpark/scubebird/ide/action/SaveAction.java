package com.techatpark.scubebird.ide.action;

import com.techatpark.scubebird.ide.ApplicationManager;
import com.techatpark.scubebird.ide.screen.BaseScreen;
import com.techatpark.scubebird.ide.screen.interfaces.FileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

final class SaveAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		BaseScreen curentScreen = ApplicationManager.getApplicationManager()
				.getCurrentScreen();
		if (curentScreen != null
				&& curentScreen instanceof FileHandler ) {
			((FileHandler) curentScreen).saveFile();
		}

	}

}
