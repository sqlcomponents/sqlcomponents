package org.scube.ide.screen;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.scube.ide.ApplicationManager;
import org.scube.ide.screen.model.SetupRequest;

public abstract class BaseScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScreenContext screenContext;
	
	private String screenTitle ;

	public String getScreenTitle() {
		return screenTitle;
	}

	public void setScreenTitle(String screenTitle) {
		this.screenTitle = screenTitle;
	}

	public ScreenContext getScreenContext() {
		return screenContext;
	}

	public void setScreenContext(ScreenContext screenContext) {
		this.screenContext = screenContext;
	}

	public void showProperties() {
	}
	
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(ApplicationManager.getApplicationManager().getDesktop(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void openModelDialog(String screenClass) {
		ApplicationManager.getApplicationManager().openModelDialog(screenClass);
	}

	public void openModelDialog(String screenClass, SetupRequest request) {
		ApplicationManager.getApplicationManager().openModelDialog(screenClass,
				request);
	}

	public void closeScreen() {
		Container parent = this;
		while ( !( parent instanceof JDialog ) 
				&& !( parent instanceof JInternalFrame) ) {
			parent = parent.getParent();
		}
		if (parent instanceof JDialog) {
			((JDialog) parent).dispose();
		}
	}
	
	public String getLabel(String key) {
		return getScreenContext().getLabel(key) ;
	}

	public BaseScreen() {
		createComponents();
		addComponents();
		sizeComponents();
		initComponents();
		initScreen();
	}

	private void initScreen() {
	}

	public void processRequest(SetupRequest setupRequest) {
		setUp(setupRequest) ;
		setupRequest.clear() ;
	}
	
	protected abstract void setUp(SetupRequest setupRequest);

	protected abstract void createComponents();

	protected abstract void sizeComponents();

	protected abstract void addComponents();

	protected abstract void initComponents();
}
