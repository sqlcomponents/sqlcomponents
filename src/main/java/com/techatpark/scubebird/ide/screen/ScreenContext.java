package com.techatpark.scubebird.ide.screen;

import java.util.ResourceBundle;

public class ScreenContext {

	private String id;
	private boolean closable;
	private boolean iconifiable;
	private boolean maximizable;
	private boolean singleInstance;

	private int width;
	private int height;
	

	public ScreenContext(String id) {
		setId(id);
		labelBundle = ResourceBundle.getBundle(id);
	}

	private ResourceBundle labelBundle;

	public String getLabel(String key) {
		return labelBundle.getString(key);
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public boolean isClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	public boolean isIconifiable() {
		return iconifiable;
	}

	public void setIconifiable(boolean iconifiable) {
		this.iconifiable = iconifiable;
	}

	public boolean isMaximizable() {
		return maximizable;
	}

	public void setMaximizable(boolean maximizable) {
		this.maximizable = maximizable;
	}

	public boolean isSingleInstance() {
		return singleInstance;
	}

	public void setSingleInstance(boolean singleInstance) {
		this.singleInstance = singleInstance;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
