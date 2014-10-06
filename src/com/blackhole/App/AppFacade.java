package com.blackhole.App;

import java.util.HashMap;

import com.blackhole.App.TodosBusiness.todo;
import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PUT;
import com.blackhole.RestRunner.Annotations.PathParam;

public class AppFacade { 
	private class ErrorDataContainer {
		public HashMap<String, Object> errors = new HashMap<String, Object>();
		// ... 
		// Hashmap<String, Object> data ... 
		public ErrorDataContainer(String field, String message) {this.errors.put(field, message);} 
	} 
	private Context mObjContext = Context.getInstance();    
	public AppFacade() {} 
	
 	@GET 
	@PATH(value="/testing")  
	public void testing999() {System.out.println("AAA");}
	
	@GET 
	@PATH(value="/getNotes")  
	public String testing() {
		System.out.println("AAA"); 
		String result = ""; 
		TodosBusiness tb = new TodosBusiness();
		todo[] todos = tb.GetTodos(); 
		if (todos != null) { 
			for (todo t : todos) {
				result += t.toString(); 
				if (result != "") { result = result + ";"; } 
			} 
		} 
		tb = null; 
		return result; 
	}
 
	@GET 
	@PATH(value="/getFilteredNotes/{rowStart}/{rowOffset}")  
	public String testingXXX(@PathParam("rowStart") String rowStart, @PathParam("rowOffset") String rowOffset) {  
		System.out.println("AAA"); 
		String result = "No results found"; 
		TodosBusiness tb = new TodosBusiness();
		todo[] todos = tb.GetTodosByLimit(Integer.parseInt(rowStart),Integer.parseInt(rowOffset)); 
		if (todos != null && todos.length>0) { 
			for (todo t : todos) { 
				result += t.toString(); 
				if (result != "") { result = result + ";"; } 
			} 
		} 
		tb = null; 
		return result; 
	}
	
	@GET
	@PATH(value="/getNote/{id}")   
	public String testing2(@PathParam("id") String id) 
	{
		Object result = new ErrorDataContainer("message", "No note found");  
		System.out.println("BBB " + id); 
		TodosBusiness tb = new TodosBusiness(); 
		todo t = tb.GetTodoById(Integer.parseInt(id)); 
		if (t != null) { 
			try {
				result = t;  
			} catch (Exception e) { 
				e.printStackTrace(); 
				result = new ErrorDataContainer("message", "Error ocurred while converting to JSON");  
			} 
		} 
		tb = null; 
		return Context.mObjUtilsInstance.ObjectToJson(result);  
	}  

	@POST 
	@PATH(value="/addNote/{title}/{desc}")  
	public void testing5(@PathParam("title") String title, @PathParam("desc") String desc) 
	{
		System.out.println("ZZZ");  
		TodosBusiness tb = new TodosBusiness(); 
		tb.InsertTodo(title, desc);  
		tb = null; 
	} 
	
	@PUT
	@PATH(value="/updateNote/{id}/{title}/{desc}")  
	public void testing3(@PathParam("id") String id, @PathParam("title") String title, @PathParam("desc") String desc) {System.out.println("CCC");}
	
	@DELETE 
	@PATH(value="/deleteNote/{id}")  
	public void testing4(@PathParam("id") String id) {System.out.println("CCC");} 
	
	@GET 
	@PATH(value="/player/isrunning") 
	public String isPlayerRunning() {
		boolean isRunning = Context.mObjMP3Player.isPlayerRunning();
		String result = ""; 
		try { 
			this.mObjContext.logInfo(String.valueOf(isRunning));
			result = Context.mObjUtilsInstance.ObjectToJson(isRunning);
		} catch (Exception e) {
			result = Context.mObjUtilsInstance.ObjectToJson("Exception ocurred while converting to JSON: " + e.getStackTrace()); 
			e.printStackTrace();  
		} 
		return result; // Fix that 
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
