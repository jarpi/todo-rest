package com.blackhole.App;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.*; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackhole.database.jdbc.SQLliteDBImpl;

public class Context {
	private static Context mInstance; 
	public static Logger mObjLog;  
	public static MP3PlayerBusiness mObjMP3Player; 
	private static SQLliteDBImpl mDBConnection;  
	private HttpServletRequest mRequest; 
	private HttpServletResponse mResponse; 
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
	
	/* public void stopMP3Player() { 
		Context.mObjMP3Player.stopPlayer(); 
		System.out.println("Context stop"); 
	} */ 
	
	/* public void setMP3PlayerVolume(int volume) {  
		// Context.mObjMP3Player.setVolume(volume);
	} */ 
	
	/* public void increaseVolume() {
		System.out.println("Context add volume"); 
		Context.mObjMP3Player.increaseVolume(); 
	}

	public void decreaseVolume() {
		System.out.println("Context del volume"); 
		Context.mObjMP3Player.decreaseVolume(); 
	} */ 

	
	/* public boolean isPlayerRunning() {
		return Context.mObjMP3Player.isPlayerRunning(); 
	} */ 
	
	public Object[] executeQuery(String sql) throws SQLException {
		return Context.mDBConnection.executeQuery(sql); 
	} 
	
	public int executeUpdate(String sql) throws SQLException {
		return Context.mDBConnection.executeUpdate(sql); 
	} 
	
	public void setRequest(HttpServletRequest request) {
		Context.getInstance().mRequest = request; 
	}
	
	public void setResponse(HttpServletResponse response) {
		Context.getInstance().mResponse = response; 
	}
	
	public void sendResponse(String result) throws IOException {
		Context.getInstance().mResponse.getWriter().print(result); 
	}
	
	public void dispose() {
		Context.mObjLog = null; 
		// Context.mObjMP3Player.stopPlayer();  
	} 
}
