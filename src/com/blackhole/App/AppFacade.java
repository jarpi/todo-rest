package com.blackhole.App;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.blackhole.App.Todos.TodosBusiness;
import com.blackhole.App.Todos.todo;
import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PUT;
import com.blackhole.RestRunner.Annotations.PathParam;
import com.blackhole.Utils.JSONObject;

public class AppFacade { 
	private Context mObjContext = null;     
	public AppFacade(Context c) {this.mObjContext = c;} 
	
	// Tested 
	@GET 
	@PATH(value="/getNotes")  
	public String testing() {  
		JSONObject result = new JSONObject("result","No notes found"); 
		TodosBusiness tb = new TodosBusiness(mObjContext);
		todo[] todos = tb.GetTodos(); 
		if (todos != null) { 
			return this.mObjContext.mObjUtilsInstance.ToJSON(todos); 
		} 
		tb = null; 
		return this.mObjContext.mObjUtilsInstance.ToJSON(result); 
	}

	// Tested 
	@GET 
	@PATH(value="/getFilteredNotes/{rowStart}/{rowOffset}")  
	public String testingXXX(@PathParam("rowStart") String rowStart, @PathParam("rowOffset") String rowOffset) {  
		JSONObject result = new JSONObject("result","No notes found");
		TodosBusiness tb = new TodosBusiness(mObjContext);
		int index = Integer.parseInt(rowStart); 
		int offSet = Integer.parseInt(rowOffset); 
		if (index<0) { 
			result = new JSONObject("error","Index out of bounds, too low"); 
		} else {
			todo[] todos = tb.GetTodosByLimit(index, offSet); 
			if (todos != null && todos.length>0) { 
				/* for (todo t : todos) { 
					result += t.toString(); 
					if (result != "") { result = result + ";"; } 
				} */ 
				return this.mObjContext.mObjUtilsInstance.ToJSON(todos);
			} 
		} 
		tb = null; 
		return this.mObjContext.mObjUtilsInstance.ToJSON(result); 
	}
	
	// Tested 
	@GET
	@PATH(value="/getNote/{id}")   
	public String testing2(@PathParam("id") String id) 
	{
		JSONObject result = new JSONObject("result","No note found");  
		System.out.println("BBB " + id); 
		TodosBusiness tb = new TodosBusiness(mObjContext); 
		todo t = tb.GetTodoById(Integer.parseInt(id)); 
		if (t != null) { 
			try {
				return this.mObjContext.mObjUtilsInstance.ToJSON(t);   
			} catch (Exception e) { 
				e.printStackTrace(); 
				result = new JSONObject("error","Error ocurred while converting to JSON");  
			} 
		} 
		tb = null; 
		return this.mObjContext.mObjUtilsInstance.ToJSON(result);  
	}  
	
	// Tested 
	@POST 
	@PATH(value="/addNote/{title}/{desc}")  
	public String testing5(@PathParam("title") String title, @PathParam("desc") String desc) 
	{  
		TodosBusiness tb = new TodosBusiness(mObjContext);
		JSONObject result = new JSONObject("result",tb.InsertTodo(title, desc));  
		tb = null; 
		return this.mObjContext.mObjUtilsInstance.ToJSON(result); 
	} 
	
	
	@POST 
	@PATH(value="/updateNote/{id}/{title}/{desc}")  
	public String testing3(@PathParam("id") String id, @PathParam("title") String title, @PathParam("desc") String desc) {
		TodosBusiness tb = new TodosBusiness(mObjContext); 
		JSONObject result = new JSONObject("result",tb.UpdateTodo(Integer.parseInt(id), title, desc)); 
		tb = null; 
		return this.mObjContext.mObjUtilsInstance.ToJSON(result);
	} 
	
	// Tested 
	@DELETE 
	@PATH(value="/deleteNote/{id}")  
	public String testing4(@PathParam("id") String id) {
		TodosBusiness tb = new TodosBusiness(mObjContext); 
		JSONObject result = new JSONObject("result", tb.DeleteTodoById(Integer.parseInt(id)));  
		tb = null;
		return this.mObjContext.mObjUtilsInstance.ToJSON(result);
	} 
	
	@GET 
	@PATH(value="/player/isrunning") 
	public String isPlayerRunning() {
		boolean isRunning = this.mObjContext.mObjMP3Player.isPlayerRunning();
		Object result = ""; 
		try { 
			this.mObjContext.logInfo(String.valueOf(isRunning));
			result = new JSONObject("result",(isRunning?"true":"false"));
		} catch (Exception e) {
			result = new JSONObject("result","Error ocurred while converting to JSON");
			e.printStackTrace();  
		} 
		return this.mObjContext.mObjUtilsInstance.ToJSON(result);  
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
		this.mObjContext.mObjMP3Player.stopPlayer(); 
	}
	
	@GET 
	@PATH(value="/volume/add") 
	public void addVolume() {
		System.out.println("Facade add volume"); 
		this.mObjContext.mObjMP3Player.increaseVolume(); 
	}
	
	@GET 
	@PATH(value="/volume/del") 
	public void delVolume() {
		System.out.println("Facade del volume"); 
		this.mObjContext.mObjMP3Player.decreaseVolume(); 
	}  
}  
