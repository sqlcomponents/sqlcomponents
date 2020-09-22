package org.scube.ide.action;

import org.scube.ide.ApplicationManager;

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
				"Dao Project","org.scube.scubedao.ide.editor.SCubeDaoEditor");
	}

}
