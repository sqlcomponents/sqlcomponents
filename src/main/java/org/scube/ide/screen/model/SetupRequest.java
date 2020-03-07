package org.scube.ide.screen.model;

import java.util.HashMap;

public final class SetupRequest {
	
	private HashMap<String, Object> requestMap ;
	
	public SetupRequest() {
		requestMap = new HashMap<String, Object>() ;
	}
	
	public void setRequestParameter(String paramKey, Object paramValue) {
		requestMap.put(paramKey, paramValue) ;
	}
	
	public Object getRequestParameter(String paramKey) { 
		return requestMap.get(paramKey) ;
	}
	
	public void clear() {
		requestMap.clear() ;
	}
	
}
