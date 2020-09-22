package com.techatpark.scubebird.ide;

import javax.swing.*;
import com.techatpark.scubebird.crawler.mysql.MySQLCrawler;

public class Main {
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

				JFrame.setDefaultLookAndFeelDecorated(true) ;
				JDialog.setDefaultLookAndFeelDecorated(true) ;
				
				ApplicationWindow.getApplicationWindow();

		});
		
	}
}
