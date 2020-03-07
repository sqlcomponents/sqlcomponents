package org.scube.ide.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.scube.ide.ApplicationManager;

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
