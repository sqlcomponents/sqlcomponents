package org.scube.ide.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.scube.ide.ApplicationManager;
import org.scube.ide.screen.BaseScreen;
import org.scube.ide.screen.interfaces.FileHandler;

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
