package com.techatpark.scubebird.ide.scubedao.ide.icon;

import javax.swing.*;
import java.awt.*;

public class UniqueIcon implements Icon {

	private final int width = 18;
    private final int height = 18;
    
    

    private Color bgColor = Color.RED;
    
    public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
            
        g2d.setColor(bgColor);        
        
        g2d.fillOval(x + 2 , y + 2, width - 4  , height - 4  ) ;
        
        g2d.dispose();
    }
    
    public int getIconWidth() {
        return width;
    }
    
    public int getIconHeight() {
        return height;
    }


}
