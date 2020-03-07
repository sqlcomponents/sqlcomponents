package org.scube.ide.screen;

import java.awt.Graphics;
import java.awt.Image;

import org.scube.ide.ApplicationManager;
import org.scube.ide.annotation.Screen;
import org.scube.ide.screen.model.SetupRequest;

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
	private Image image ;
	
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
