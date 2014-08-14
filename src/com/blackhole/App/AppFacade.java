package com.blackhole.App;

import java.io.IOException;

import com.blackhole.App.TodosBusiness.todo;
import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PUT;
import com.blackhole.RestRunner.Annotations.PathParam;

public class AppFacade {
	private Context mObjContext = new Context();  
	private TodosBusiness mTodosBusiness = new TodosBusiness(); 
	public AppFacade() {} 
	
 	@GET 
	@PATH(value="/testing")  
	public void testing999() {System.out.println("AAA");}
	
	@GET 
	@PATH(value="/getNotes")  
	public void testing() {System.out.println("AAA");}
 
	@GET
	@PATH(value="/getNote/{id}")   
	public void testing2(@PathParam("id") String id) 
	{
		System.out.println("BBB " + id); 
		todo t = mTodosBusiness.GetTodoById(Integer.parseInt(id)); 
	}  

	@POST 
	@PATH(value="/addNote/{title}/{desc}")  
	public void testing5(@PathParam("title") String title, @PathParam("desc") String desc) 
	{
		System.out.println("ZZZ");  
		mTodosBusiness.InsertTodo(title, desc); 
	} 
	
	@PUT
	@PATH(value="/updateNote/{id}/{title}/{desc}")  
	public void testing3(@PathParam("id") String id, @PathParam("title") String title, @PathParam("desc") String desc) {System.out.println("CCC");}
	
	@DELETE 
	@PATH(value="/deleteNote/{id}")  
	public void testing4(@PathParam("id") String id) {System.out.println("CCC");} 
	
	@GET 
	@PATH(value="/player/isrunning") 
	public void isPlayerRunning() {
		boolean isRunning = Context.mObjMP3Player.isPlayerRunning();
		try { 
			this.mObjContext.sendResponse(String.valueOf(isRunning)); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	} 
	
	@GET 
	@PATH(value="/play/flaixfm") 
	public void playFlaixFmRadio() {
		this.mObjContext.setNewMP3Player("http://195.10.10.219/flaix/shoutcastmp3.mp3"); 
		// this.mObjContext.setNewMP3Player("/tmp/test.mp3");
	} 
	
	@GET 
	@PATH(value="/stop/flaixfm") 
	public void stopFlaixFmRadio() {
		System.out.println("Facade stop"); 
		Context.mObjMP3Player.stopPlayer(); 
	}
	
	@GET 
	@PATH(value="/volume/add") 
	public void addVolume() {
		System.out.println("Facade add volume"); 
		Context.mObjMP3Player.increaseVolume(); 
	}
	
	@GET 
	@PATH(value="/volume/del") 
	public void delVolume() {
		System.out.println("Facade del volume"); 
		Context.mObjMP3Player.decreaseVolume(); 
	}
	
	/* @POST 
	@PATH(value="/set/flaixfm/volume/{vol}") 
	public void setVolumeFlaixFmRadio(@PathParam("vol") String vol) {
		this.mObjContext.setMP3PlayerVolume(Integer.parseInt(vol)); 
	} */ 
} 
