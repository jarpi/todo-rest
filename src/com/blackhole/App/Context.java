package com.blackhole.App;

import java.util.logging.*; 

public class Context {
	private static Context mInstance; 
	private static Logger mObjLog;  
	// TODO: 
	// - Configure logging instance 
	// - Encapsulate data access (create new connection to DB) 
	protected Context() { 
		Context.mObjLog = Logger.getLogger(Context.class.getName());
	}  
	// public MP3Player player = new MP3Player(flaixFMUrl); 
	
	public static Context getInstance() {
		if (Context.mInstance == null) {
			Context.mInstance = new Context(); 
		}
		return Context.mInstance; 
	}
	
	public void logInfo(String msg) {
		Context.mObjLog.log(Level.INFO, msg); 
	} 
	
	public void logError(String msg) {
		Context.mObjLog.log(Level.SEVERE, msg); 
	}
	
	public void logWarning(String msg) { 
		Context.mObjLog.log(Level.INFO, msg); 
	}
	
	public void setNewMP3Player(String url) { 
		MP3Player.getInstance().startPlayer(url); 
	} 
	
	public void stopMP3Player() { 
		MP3Player.getInstance().stopPlayer();
	} 
	
	public void setMP3PlayerVolume(int volume) {  
		MP3Player.getInstance().setVolume(volume);
	} 
	
	public void dispose() {
		Context.mObjLog = null; 
		MP3Player.getInstance().stopPlayer(); 
	}
}
