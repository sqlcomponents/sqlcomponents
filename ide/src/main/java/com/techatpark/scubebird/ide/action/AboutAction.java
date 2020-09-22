package com.techatpark.scubebird.ide.action;

import com.techatpark.scubebird.ide.ApplicationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

final class AboutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {

		ApplicationManager
				.getApplicationManager()
				.openModelDialog("com.techatpark.scubebird.ide.screen.AboutScreen");
	}

}
