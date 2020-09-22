package org.scube.ide.action;

import org.scube.ide.ApplicationManager;

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
				.openModelDialog("org.scube.ide.screen.AboutScreen");
	}

}
