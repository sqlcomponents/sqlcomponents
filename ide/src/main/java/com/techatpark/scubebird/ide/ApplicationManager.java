package com.techatpark.scubebird.ide;

import com.techatpark.scubebird.ide.screen.BaseScreen;
import com.techatpark.scubebird.ide.screen.ScreenContext;
import com.techatpark.scubebird.ide.screen.ScreenFactory;
import com.techatpark.scubebird.ide.screen.interfaces.FileHandler;
import com.techatpark.scubebird.ide.screen.model.SetupRequest;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class ApplicationManager implements InternalFrameListener {

	private static ApplicationManager applicationManager;

	public static final ApplicationManager getApplicationManager() {
		if (applicationManager == null) {
			applicationManager = new ApplicationManager();
		}
		return applicationManager;
	}

	private HashMap<String, ImageIcon> imageiconMaps;
	private ResourceBundle labelBundle;

	private final ArrayList<JInternalFrame> internalFrames;

	private JDesktopPane desktop;

	private BaseScreen currentScreen;

	public BaseScreen getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(BaseScreen currentScreen) {
		this.currentScreen = currentScreen;
	}

	private ApplicationManager() {
		internalFrames = new ArrayList<JInternalFrame>(5);
		loadApplicationResources();
	}

	public JDesktopPane getDesktop() {
		return desktop;
	}

	public void setDesktop(JDesktopPane desktop) {
		this.desktop = desktop;
	}

	private void loadApplicationResources() {
		imageiconMaps = new HashMap<String, ImageIcon>();

		Enumeration<String> keys = null;
		String key = null;

		ResourceBundle iconBundle = ResourceBundle
				.getBundle("properties.icons");
		keys = iconBundle.getKeys();
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			imageiconMaps.put(key, new ImageIcon(ApplicationManager.class
					.getResource(iconBundle.getString(key))));
		}
		labelBundle = ResourceBundle.getBundle("properties.labels");
	}

	/**
	 * Resource Related
	 */
	public String getLabel(String key) {
		return labelBundle.getString(key);
	}

	public ImageIcon getIcon(String key) {
		return imageiconMaps.get(key);
	}

	private JInternalFrame getInternalFrame() {
		if (internalFrames.size() > 0) {
			return internalFrames.remove(0);
		} else {
			return new JInternalFrame();
		}
	}

	/**
	 * Chooser related
	 */
	public void openFile(File file, String editorClass) {
		BaseScreen baseScreen = ScreenFactory.getScreenFactory().getScreen(
				editorClass);
		if (baseScreen instanceof FileHandler) {
			if(((FileHandler) baseScreen).openFile(file)) {
				openScreen(baseScreen);
			}
			else {
				baseScreen.showErrorMessage("File not found !!") ;
			}
		}
		
	}
	
	/**
	 * Chooser related
	 */
	public File openFile(String extension, String description) {		
		return openFile(extension,description,null);

	}

	/**
	 * Chooser related
	 */
	public File openFile(String extension, String description,
			String editorClass) {
		File file = ApplicationWindow.getApplicationWindow()
				.showOpenFileDialog(extension, description);
		if( file != null && editorClass != null) {			
			openFile(file, editorClass);
		}		
		return file;

	}
	
	/**
	 * Chooser related
	 */
	public File openFileFolder() {
		File file = ApplicationWindow.getApplicationWindow()
				.showOpenFileFolderDialog();		
		return file;

	}

	public void openScreen(Class<? extends BaseScreen> screenClass,
			SetupRequest request) {

		BaseScreen baseScreen = ScreenFactory.getScreenFactory().getScreen(
				screenClass);
		openScreen(baseScreen);
		if (request != null) {
			baseScreen.processRequest(request);
		}

	}

	private void openScreen(BaseScreen screen) {
		JInternalFrame internalFrame = getInternalFrame();
		ScreenContext screenContext = screen.getScreenContext();
		internalFrame.addInternalFrameListener(this);
		internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		internalFrame.setTitle(screen.getLabel("title") + " - " +(screen.getScreenTitle() == null ? "" : screen
				.getScreenTitle()));
		internalFrame.setSize(screenContext.getWidth(), screenContext
				.getHeight());
		internalFrame.setClosable(screenContext.isClosable());
		internalFrame.setMaximizable(screenContext.isMaximizable());
		internalFrame.setIconifiable(screenContext.isIconifiable());
		internalFrame.setContentPane(screen);
		internalFrame.setVisible(true);
		internalFrame.setLocation(10, 10);
		desktop.add(internalFrame, 3);
		makeFrameVisible(internalFrame);

	}

	/**
	 * makes the internal frame visible
	 * 
	 * @param baseInternalFrame
	 */
	private void makeFrameVisible(JInternalFrame jInternalFrame) {
		jInternalFrame.setVisible(true);
		try {
			jInternalFrame.setSelected(true);
			jInternalFrame.setIcon(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openModelDialog(String screenClass) {
		openModelDialog(screenClass, null);
	}

	public void openModelDialog(String screenClass, SetupRequest request) {
		BaseScreen baseScreen = ScreenFactory.getScreenFactory().getScreen(
				screenClass);
		if (request != null) {
			baseScreen.processRequest(request);
		}
		ApplicationWindow.getApplicationWindow().openModelDialog(baseScreen);

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		JInternalFrame internalFrame = (JInternalFrame) arg0.getSource();
		setCurrentScreen((BaseScreen) internalFrame.getContentPane());
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) {
		// TODO Auto-generated method stub

	}

}
