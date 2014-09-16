package com.blackhole.App;

import com.blackhole.App.TodosBusiness.todo;
import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PUT;
import com.blackhole.RestRunner.Annotations.PathParam;

public class AppFacade {
	private Context mObjContext = new Context();   
	public AppFacade() {} 
	
 	@GET 
	@PATH(value="/testing")  
	public void testing999() {System.out.println("AAA");}
	
	@GET 
	@PATH(value="/getNotes")  
	public String testing() {
		System.out.println("AAA"); 
		String result = ""; 
		todo[] todos = mObjContext.mTodosBusiness.GetTodos(); 
		if (todos != null) { 
			for (todo t : todos) {
				result += t.toString(); 
				if (result != "") { result = result + ";"; } 
			} 
		} 
		return result; 
	}
 
	@GET 
	@PATH(value="/getFilteredNotes/{rowStart}/{rowOffset}")  
	public String testingXXX(@PathParam("rowStart") String rowStart, @PathParam("rowOffset") String rowOffset) {  
		System.out.println("AAA"); 
		String result = ""; 
		todo[] todos = mObjContext.mTodosBusiness.GetTodosByLimit(Integer.parseInt(rowStart),Integer.parseInt(rowOffset)); 
		if (todos != null) { 
			for (todo t : todos) { 
				result += t.toString(); 
				if (result != "") { result = result + ";"; } 
			} 
		} 
		return result; 
	}
	
	@GET
	@PATH(value="/getNote/{id}")   
	public String testing2(@PathParam("id") String id) 
	{
		String result = "No note found"; 
		System.out.println("BBB " + id); 
		todo t = mObjContext.mTodosBusiness.GetTodoById(Integer.parseInt(id)); 
		if (t != null) {
			result = t.toString(); 
		} 
		return result; 
	}  

	@POST 
	@PATH(value="/addNote/{title}/{desc}")  
	public void testing5(@PathParam("title") String title, @PathParam("desc") String desc) 
	{
		System.out.println("ZZZ");  
		mObjContext.mTodosBusiness.InsertTodo(title, desc); 
	} 
	
	@PUT
	@PATH(value="/updateNote/{id}/{title}/{desc}")  
	public void testing3(@PathParam("id") String id, @PathParam("title") String title, @PathParam("desc") String desc) {System.out.println("CCC");}
	
	@DELETE 
	@PATH(value="/deleteNote/{id}")  
	public void testing4(@PathParam("id") String id) {System.out.println("CCC");} 
	
	@GET 
	@PATH(value="/player/isrunning") 
	public boolean isPlayerRunning() {
		boolean isRunning = Context.mObjMP3Player.isPlayerRunning();
		try { 
			this.mObjContext.logInfo(String.valueOf(isRunning));  
			// this.mObjContext.sendResponse(String.valueOf(isRunning)); 
			return isRunning; 
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return false; 
	} 
	
	@GET 
	@PATH(value="/play/flaixfm") 
	public void playFlaixFmRadio() {
		this.mObjContext.setNewMP3Player(this.mObjContext.getProperty("playUrl")); 
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
} 
