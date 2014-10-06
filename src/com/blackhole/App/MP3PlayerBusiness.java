package com.blackhole.App;

import java.io.OutputStream;

public class MP3PlayerBusiness {
	
	/* private static MP3PlayerBusiness mInstance; */  
	private String mUrlToPlay = ""; 
	private Process mProcess = null; 
	
	protected MP3PlayerBusiness() { 
		
	}
	
	/* public static MP3PlayerBusiness getInstance() { 
		if (MP3PlayerBusiness.mInstance == null) {
			MP3PlayerBusiness.mInstance = new MP3PlayerBusiness(); 
		} 
		return MP3PlayerBusiness.mInstance; 
	} */  
	
	public void startPlayer(String url) { 
		Context.getInstance().logInfo("Start player"); 
		this.mUrlToPlay = url;  
		this.run(); 
	} 
	
	
	public void run() { 
		if (this.mProcess == null) { 
			try {
				java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		        this.mProcess = rt.exec("omxplayer --vol -2100 " + this.mUrlToPlay);  
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
		} 
	} 

	public void stopPlayer() { 
		Context.getInstance().logInfo("Stop player"); 
		if (this.mProcess != null) { 
			try {
				OutputStream os = this.mProcess.getOutputStream();
				os.write("q".getBytes()); 
				os.flush(); 
				this.mProcess.waitFor(); 
				int i = this.mProcess.exitValue(); 
				os = null; 
				this.mProcess = null; 
			} catch (Exception e) {
				e.printStackTrace(); 
			} finally {
				this.mProcess = null;  
			} 
		} 
	} 
	
	public void increaseVolume() { 
		Context.getInstance().logInfo("Increase volume"); 
		if (this.mProcess != null) {
			try { 
				OutputStream os = this.mProcess.getOutputStream();
				os.write("+".getBytes()); 
				os.flush(); 
				os = null; 
			} catch (Exception e) {
				e.printStackTrace(); 
			} 
		}
	}
	
	
	public void decreaseVolume() {
		Context.getInstance().logInfo("Decrease volume"); 
		if (this.mProcess != null) {
			try {
				OutputStream os = this.mProcess.getOutputStream();
				os.write("-".getBytes());
				os.flush(); 
				os = null; 
			} catch (Exception e) {
				e.printStackTrace(); 
			} 
		} 
	} 
	
	public boolean isPlayerRunning() { 
		Context.getInstance().logInfo("Is player running");  
		return (this.mProcess!=null); 
	}
} 
