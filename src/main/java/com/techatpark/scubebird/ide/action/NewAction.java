package com.techatpark.scubebird.ide.action;

import com.techatpark.scubebird.ide.ApplicationManager;
import com.techatpark.scubebird.ide.screen.model.SetupRequest;
import com.techatpark.scubebird.scubedao.constants.RequestContants;
import com.techatpark.scubebird.scubedao.model.DaoProject;

import javax.swing.*;
import java.awt.event.ActionEvent;

final class NewAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		SetupRequest request = new SetupRequest();
		request.setRequestParameter(RequestContants.PARAM_DAO_PROJECT,
				new DaoProject());
		ApplicationManager.getApplicationManager().openModelDialog(
				"com.techatpark.scubebird.scubedao.ide.propertyeditor.IcgProjectScreen",request);
	}

}
