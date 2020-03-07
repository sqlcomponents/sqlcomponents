package org.scube.ide.screen;

import java.util.HashMap;

import org.scube.ide.annotation.Screen;

public final class ScreenFactory {

	private static ScreenFactory screenFactory;

	public static ScreenFactory getScreenFactory() {
		if (screenFactory == null) {
			screenFactory = new ScreenFactory();
		}
		return screenFactory;
	}
	
	private ScreenFactory() {
		screenCahe = new HashMap<String, BaseScreen>() ;
		screenCtxCahe = new HashMap<String, ScreenContext>() ;
	}

	HashMap<String, BaseScreen> screenCahe;

	HashMap<String, ScreenContext> screenCtxCahe;

	public BaseScreen getScreen(Class< ? extends BaseScreen> screenClass) {
		String id = screenClass.getName() ;
		BaseScreen screen = screenCahe.get(id);
		if (screen == null) {
			ScreenContext screenContext = getScreenContext(screenClass);
			try {
				screen = (BaseScreen) Class.forName(id).newInstance() ;
				screen.setScreenContext(screenContext) ;
				if( screenContext.isSingleInstance() ) {
					screenCahe.put(id, screen) ;
				}				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return screen;
	}
	
	@SuppressWarnings("unchecked")
	public BaseScreen getScreen(String id) {
		BaseScreen baseScreen = null ;
		try {
			baseScreen = getScreen((Class<? extends BaseScreen>) Class.forName(id)) ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseScreen ;
	}

	private ScreenContext getScreenContext(Class< ? extends BaseScreen> screenClass) {
		String id = screenClass.getName() ;
		ScreenContext screenContext = screenCtxCahe.get(id);
		if (screenContext == null) {			
			screenContext = getScreenContext(screenClass.getAnnotation(Screen.class),id);
			screenCtxCahe.put(id, screenContext);
		}	
		return screenContext ;
	}

	private ScreenContext getScreenContext(Screen screen,String id) {
		ScreenContext screenContext = new ScreenContext(id);		
		screenContext.setClosable(screen.closeable()) ;
		screenContext.setIconifiable(screen.iconifiable()) ;
		screenContext.setHeight(screen.height()) ;
		screenContext.setMaximizable(screen.maximizable()) ;
		screenContext.setSingleInstance(screen.singleInstance());
		screenContext.setWidth(screen.width()) ;
		return screenContext;
	}

	

}
