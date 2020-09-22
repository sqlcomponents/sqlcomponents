package com.techatpark.scubebird.ide;

import com.techatpark.scubebird.ide.action.ActionFactory;
import com.techatpark.scubebird.ide.screen.BaseScreen;
import com.techatpark.scubebird.ide.screen.ScreenContext;
import com.techatpark.scubebird.ide.util.ApplicationFileFilter;
import com.techatpark.scubebird.scubedao.ide.action.RunAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public final class ApplicationWindow {

	private static ApplicationWindow applicationWindow;

	private ApplicationManager applicationManager = ApplicationManager
			.getApplicationManager();

	public static final ApplicationWindow getApplicationWindow() {
		if (applicationWindow == null) {
			applicationWindow = new ApplicationWindow();
		}
		return applicationWindow;
	}

	private JFrame mainFrame;

	private JPanel contentPane;
	private JMenuBar mainMenuBar;
	private JToolBar mainToolbar;
	private JDesktopPane desktop;
	private JPanel footerPane;

	JDialog modelDlg;
	private JFileChooser fileChooser;

	private JButton newBtn;
	private JButton openBtn;
	private JButton saveBtn;
	private JButton runBtn;

	private ApplicationWindow() {

		createComponents();
		addComponents();
		initComponents();

		constructFileMenu();
		constructWindowMenu();
		constructHelpMenu();

		applicationManager.setDesktop(desktop);

		// loadApplicationXML() ;

	}

	private void createComponents() {

		mainFrame = new JFrame();
		contentPane = new JPanel();
		mainMenuBar = new JMenuBar();
		mainToolbar = new JToolBar();
		desktop = new JDesktopPane();
		footerPane = new JPanel();

		fileChooser = new JFileChooser();
		modelDlg = new JDialog(mainFrame);

		newBtn = new JButton(ActionFactory.getNewAction());
		openBtn = new JButton(ActionFactory.getOpenAction());
		saveBtn = new JButton(ActionFactory.getSaveAction());
		runBtn = new JButton(new RunAction());
	}

	private void addComponents() {

		contentPane.setLayout(new BorderLayout());
		contentPane.add(mainToolbar, BorderLayout.NORTH);
		contentPane.add(desktop, BorderLayout.CENTER);
		contentPane.add(footerPane, BorderLayout.SOUTH);

		mainToolbar.add(newBtn);
		mainToolbar.add(openBtn);
		mainToolbar.add(saveBtn);
		mainToolbar.add(runBtn);

		mainFrame.setJMenuBar(mainMenuBar);
		mainFrame.add(contentPane);
	}

	private void initComponents() {

		modelDlg.setModal(true);
		modelDlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// register Escape Key
		modelDlg.getRootPane().registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				modelDlg.dispose();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		newBtn.setIcon(applicationManager.getIcon("new"));
		openBtn.setIcon(applicationManager.getIcon("open"));
		saveBtn.setIcon(applicationManager.getIcon("save"));
		runBtn.setIcon(applicationManager.getIcon("run"));

		mainFrame.setIconImage(((ImageIcon) applicationManager
				.getIcon("icon.application")).getImage());

		mainToolbar.setFloatable(false);

		mainFrame.setSize(500, 500);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setTitle(applicationManager.getLabel("appliation.name"));
		mainFrame.setExtendedState(mainFrame.getExtendedState()
				| JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
	}

	/**
	 * constructs the File Menu.
	 * 
	 */
	private void constructFileMenu() {
		JMenu fileMenu = new JMenu(applicationManager.getLabel("menu.file"));
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem menuItem = null;

		menuItem = new JMenuItem(ActionFactory.getNewAction());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		menuItem.setText(applicationManager.getLabel("menu.new"));
		menuItem.setMnemonic(KeyEvent.VK_N);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(ActionFactory.getOpenAction());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		menuItem.setText(applicationManager.getLabel("menu.open"));
		menuItem.setMnemonic(KeyEvent.VK_O);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(ActionFactory.getSaveAction());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItem.setText(applicationManager.getLabel("menu.save"));
		menuItem.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(ActionFactory.getPropertyAction());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		menuItem.setText(applicationManager.getLabel("menu.properties"));
		menuItem.setMnemonic(KeyEvent.VK_P);
		fileMenu.add(menuItem);

		fileMenu.addSeparator();

		menuItem = new JMenuItem(applicationManager.getLabel("menu.close"));

		menuItem.setMnemonic(KeyEvent.VK_C);
		fileMenu.add(menuItem);

		mainMenuBar.add(fileMenu);
	}

	/**
	 * constructs the Window Menu.
	 * 
	 * @param sourceMenu
	 *            the source menu to apply the menu items
	 * @param tileMode
	 *            the current tile mode
	 */
	private void constructWindowMenu() {
		JMenu windowMenu = new JMenu(applicationManager.getLabel("menu.window"));
		windowMenu.setMnemonic(KeyEvent.VK_W);

		JMenuItem menuItem = null;

		menuItem = new JMenuItem(applicationManager.getLabel("menu.tile"));
		menuItem.setMnemonic(KeyEvent.VK_T);
		windowMenu.add(menuItem);

		menuItem = new JMenuItem(applicationManager.getLabel("menu.cascade"));
		menuItem.setMnemonic(KeyEvent.VK_C);
		windowMenu.add(menuItem);

		mainMenuBar.add(windowMenu);
	}

	/**
	 * constructs the Help Menu.
	 * 
	 * @param sourceMenu
	 *            the source menu to apply the menu items
	 * @param tileMode
	 *            the current tile mode
	 */
	private void constructHelpMenu() {
		JMenu helpMenu = new JMenu(applicationManager.getLabel("menu.help"));
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem menuItem = null;

		menuItem = new JMenuItem();
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuItem.setText(applicationManager.getLabel("menu.helpcontents"));
		menuItem.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(menuItem);

		menuItem = new JMenuItem(ActionFactory.getAboutAction());
		menuItem.setText(applicationManager.getLabel("menu.about"));
		menuItem.setMnemonic(KeyEvent.VK_A);
		helpMenu.add(menuItem);

		mainMenuBar.add(helpMenu);
	}

	File showOpenFileFolderDialog() {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	File showOpenFileDialog(String extension, String description) {
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new ApplicationFileFilter(extension,
				description));
		if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	public File showSaveFileDialog(String extension, String description) {
		fileChooser.setFileFilter(new ApplicationFileFilter(extension,
				description));
		if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;

	}

	void openModelDialog(BaseScreen screen) {
		Container contentPane = modelDlg.getContentPane();
		if (contentPane != screen) {
			ScreenContext screenContext = screen.getScreenContext();
			Dimension dimension = new Dimension(screenContext.getWidth(),
					screenContext.getHeight());
			screen.setPreferredSize(dimension);
			screen.setMaximumSize(dimension);
			screen.setMinimumSize(dimension);
			modelDlg.setTitle(screen.getLabel("title")
					+ " - "
					+ (screen.getScreenTitle() == null ? "" : screen
							.getScreenTitle()));
			modelDlg.setContentPane(screen);
			modelDlg.pack();
			modelDlg.setLocationRelativeTo(null);
		}
		modelDlg.setVisible(true);
	}

}
