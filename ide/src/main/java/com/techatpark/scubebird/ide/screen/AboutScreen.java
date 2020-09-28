package com.techatpark.scubebird.ide.screen;

import com.techatpark.scubebird.ide.ApplicationManager;
import com.techatpark.scubebird.ide.annotation.Screen;
import com.techatpark.scubebird.ide.screen.model.SetupRequest;

import java.awt.*;

@Screen(
		closeable=false,
		singleInstance=true,
		width=400,
		height=300
)
public class AboutScreen extends BaseScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Image image ;
	
	public AboutScreen() {
		
		image = ApplicationManager.getApplicationManager().getIcon("about").getImage() ;
	}
	
	@Override
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
		if (image != null) {
		      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
		}
	}
	
	@Override
	protected void addComponents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createComponents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setUp(SetupRequest setupRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void sizeComponents() {
		// TODO Auto-generated method stub

	}

}
