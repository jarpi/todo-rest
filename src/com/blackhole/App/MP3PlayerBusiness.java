package com.blackhole.App;

import java.net.URL;

import javax.media.Format;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat; 

public class MP3PlayerBusiness extends Thread {
	
	private static MP3PlayerBusiness mInstance; 
	private Player mPlayer; 
	private String mUrlToPlay = ""; 
	
	protected MP3PlayerBusiness() { 
		if (this.mPlayer == null) { 
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 
	
	public static MP3PlayerBusiness getInstance() { 
		if (MP3PlayerBusiness.mInstance == null) {
			MP3PlayerBusiness.mInstance = new MP3PlayerBusiness(); 
		} 
		return MP3PlayerBusiness.mInstance; 
	} 
	
	public void startPlayer(String url) {
		this.mUrlToPlay = url; 
		run(); 
	}
	
	public void run() {
		if (this.mPlayer == null) {
			try { 
				Manager.setHint(Manager.CACHING, false);
				Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true); 
				this.mPlayer = Manager.createPlayer(new URL(mUrlToPlay));  
				this.mPlayer.start(); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	} 
	
	public void setVolume(int vol) { 
		if (this.mPlayer != null) { 
			if (vol < 9) {   
				float f = (float) vol/10; 
				this.setVolumeWithEffect(f); 
			}  
		} 
	} 
	
	public void stopPlayer() { 
		if (this.mPlayer != null) { 
			this.mPlayer.stop(); 
			this.mPlayer.close(); 
			this.mPlayer.deallocate(); 
			this.mPlayer = null; 
		} 
	} 
	
	private void setVolumeWithoutEffect(float vol) {
		this.mPlayer.getGainControl().setLevel(vol); 
	} 
	
	private void setVolumeWithEffect(float vol) { 
		float  currentVol = this.mPlayer.getGainControl().getLevel();
		float increment = 0.01f;  
		if (vol < currentVol) {
			increment = -0.01f; 
		} 
		while (currentVol != vol) { 
			currentVol += increment; 
			currentVol = (float) Math.round(currentVol*100)/100; 
			this.mPlayer.getGainControl().setLevel(currentVol);
			currentVol = this.mPlayer.getGainControl().getLevel(); 
			try { 
				sleep(100); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} 
	} 
} 
