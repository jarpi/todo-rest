package com.blackhole.App;

import java.util.logging.*; 

public class Context {
	// private static final String flaixFMUrl = ""; 
	private Logger logInstance; 
	// TODO: 
	// - Configure logging instance 
	// - Encapsulate data access (create new connection to DB) 
	public Context() {} 
	// public MP3Player player = new MP3Player(flaixFMUrl); 
	
	public Logger getLoggingInstance() {
		if (logInstance == null) {
			logInstance = Logger.getLogger(Context.class.getName());  
		} 
		return this.logInstance;
	} 
	
	public void dispose() {
		this.logInstance = null; 
	}
}
