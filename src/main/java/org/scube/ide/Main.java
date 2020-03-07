package org.scube.ide;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true) ;
				JDialog.setDefaultLookAndFeelDecorated(true) ;
				
				ApplicationWindow.getApplicationWindow();
			}
		});
		
	}
}
