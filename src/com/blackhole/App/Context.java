package com.blackhole.App;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.*; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackhole.database.jdbc.SQLliteDBImpl;

public class Context {
	private static Context mInstance; 
	public static Logger mObjLog;  
	public static MP3PlayerBusiness mObjMP3Player; 
	private static SQLliteDBImpl mDBConnection;  
	// TODO: 
	// - Configure logging instance 
	// - Encapsulate data access (create new connection to DB) 
	protected Context() { 
		Context.mObjLog = Logger.getLogger(Context.class.getName());
		mObjMP3Player = MP3PlayerBusiness.getInstance(); 
		mDBConnection = SQLliteDBImpl.getInstance(); 
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
		Context.mObjMP3Player.startPlayer(url); 
	} 
	
	public Object[] executeQuery(String sql) throws SQLException {
		return Context.mDBConnection.executeQuery(sql); 
	} 
	
	public int executeUpdate(String sql) throws SQLException {
		return Context.mDBConnection.executeUpdate(sql); 
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
	}
	
	public void dispose() {
		Context.mObjLog = null; 
		// Context.mObjMP3Player.stopPlayer();  
	} 
}
