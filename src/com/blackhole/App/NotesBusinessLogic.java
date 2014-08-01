package com.blackhole.App;

import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PUT;
import com.blackhole.RestRunner.Annotations.PathParam;

public class NotesBusinessLogic {
	private Context mObjContext = new Context();  
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
		this.mObjContext.setNewMP3Player("http://195.10.10.221/flaix/shoutcastmp3.mp3"); 
	} 
	
	@GET 
	@PATH(value="/stop/flaixfm") 
	public void stopFlaixFmRadio() {
		 this.mObjContext.stopMP3Player(); 
	}
	
	@POST 
	@PATH(value="/set/flaixfm/volume/{vol}") 
	public void setVolumeFlaixFmRadio(@PathParam("vol") String vol) {
		this.mObjContext.setMP3PlayerVolume(Integer.parseInt(vol)); 
	} 
} 
