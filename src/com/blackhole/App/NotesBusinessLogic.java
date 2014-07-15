package com.blackhole.App;

import java.net.URL;

import javax.media.Format;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.stage.Stage;


import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PUT;
import com.blackhole.RestRunner.Annotations.PathParam;

public class NotesBusinessLogic {
	public NotesBusinessLogic() {} 
	
 	@GET 
	@PATH(value="/testing")  
	public void testing999() {System.out.println("AAA");}
	
	@GET 
	@PATH(value="/getNotes")  
	public void testing() {System.out.println("AAA");}
 
	@GET
	@PATH(value="/getNote/{id}")   
	public void testing2(@PathParam("id") String id) {System.out.println("BBB " + id);}  
 
	@POST 
	@PATH(value="/addNote/{title}/{desc}")  
	public void testing5(@PathParam("title") String id, @PathParam("desc") String desc) {System.out.println("ZZZ");} 
	
	@PUT
	@PATH(value="/updateNote/{id}/{title}/{desc}")  
	public void testing3(@PathParam("id") String id, @PathParam("title") String title, @PathParam("desc") String desc) {System.out.println("CCC");}
	
	@DELETE 
	@PATH(value="/deleteNote/{id}")  
	public void testing4(@PathParam("id") String id) {System.out.println("CCC");} 
	
	@GET 
	@PATH(value="/play/flaixfm") 
	public void playFlaixFmRadio() {
		new MP3Player("http://195.10.10.220/flaix/flaixfmnopub.mp3"); 
	} 
	
	@GET 
	@PATH(value="/stop/flaixfm") 
	public void stopFlaixFmRadio() {
		new MP3Player("").stopPlayer(); 
	}
	
	@POST 
	@PATH(value="/set/flaixfm/volume/{vol}") 
	public void setVolumeFlaixFmRadio(@PathParam("vol") float vol) {
		new MP3Player("").setVolume(vol);  
	} 
}
