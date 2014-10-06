package com.blackhole.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.*; 


import com.blackhole.Utils.Utils;
import com.blackhole.database.jdbc.SQLliteDBImpl;

public class Context { 
	private static Context mInstance; 
	public static Logger mObjLog;  
	public static MP3PlayerBusiness mObjMP3Player; 
	public static SQLliteDBImpl mDBConnection;  
	public static Utils mObjUtilsInstance; 
	// TODO: 
	// - Configure logging instance 
	// - Encapsulate data access (create new connection to DB) 
	public Context() { 
		Context.mObjLog = Logger.getLogger(Context.class.getName());  
		mDBConnection = SQLliteDBImpl.getInstance(); 
		mObjUtilsInstance = Utils.getInstance(); 
	}  
	
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
		Context.mObjMP3Player.startPlayer(url); 
	} 
		
	public String getProperty(String name) {
		Properties prop = new Properties();
		InputStream is = null; 
		String result = ""; 
		try {
			is = Context.class.getClassLoader().getResourceAsStream("configuration.properties");
			prop.load(is); 
			result = prop.getProperty(name); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
		return result; 
	}
	
	public void setPropertyValue(String name, String value) {
		// TO DO 
		Properties prop = new Properties();
		InputStream is = null; 
		try {
			is = Context.class.getClassLoader().getResourceAsStream("configuration.properties");
			prop.load(is); 
			prop.setProperty(name, value);  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
	}
	
	public void dispose() {
		Context.mObjLog = null; 
	} 
}
