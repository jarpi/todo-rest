package com.blackhole.App;

import java.net.URL;

import javax.media.Format;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

public class MP3Player extends Thread {
	
	private Player player; 
	private String urlToPlay = ""; 
	
	public MP3Player(String url) { 
		if (this.player == null) {
			this.urlToPlay = url; 
			try{
				Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
				// Format input2 = new AudioFormat(AudioFormat.MPEG);
				Format output = new AudioFormat(AudioFormat.LINEAR);
				PlugInManager.addPlugIn(
					"com.sun.media.codec.audio.mp3.JavaDecoder",
					new Format[]{input1},
					new Format[]{output},
					PlugInManager.CODEC
				);  
				run(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 
	
	public void run() {
		if (this.player == null) {
			try { 
				Manager.setHint(Manager.CACHING, false);
				Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true); 
				this.player = Manager.createPlayer(new URL(urlToPlay));  
				this.player.start(); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	} 
	
	public void setVolume(float vol) { 
		if (this.player != null) {
			this.player.getGainControl().setLevel(vol);
		} 
	} 
	
	public void stopPlayer() { 
		if (this.player != null) {
			this.player.close(); 
			this.player.deallocate();
		} 
	} 
} 
