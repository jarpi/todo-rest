package com.blackhole.App;

import com.blackhole.RestRunner.Annotations.DELETE;
import com.blackhole.RestRunner.Annotations.GET;
import com.blackhole.RestRunner.Annotations.POST;
import com.blackhole.RestRunner.Annotations.PATH;
import com.blackhole.RestRunner.Annotations.PathParam;
import com.blackhole.RestRunner.Annotations.UPDATE;

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
	public void testing2(@PathParam("id") String id) {System.out.println("BBB");} 
	
	@POST 
	@PATH(value="/addNote/{id}") 
	public void testing5(@PathParam("id") String id) {System.out.println("ZZZ");} 
	
	@UPDATE
	@PATH(value="/updateNote/{id}")  
	public void testing3(@PathParam("id") String id) {System.out.println("CCC");}
	
	@DELETE 
	@PATH(value="/deleteNote/{id}")  
	public void testing4(@PathParam("id") String id) {System.out.println("CCC");} 
	
}
