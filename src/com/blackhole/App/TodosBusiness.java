package com.blackhole.App;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodosBusiness { 
	class todo { 
		private int id = 0;  
		private String title, desc; 
		public todo(String title, String desc) {this.title = title; this.desc = desc;} 
		public todo(int id, String title, String desc) {}
		public int getId() {return this.id;} 
		public String getTitle() {return this.title;} 
		public String getDesc()  {return this.desc;} 
		public int InsertToDb() {
			String sql = "INSERT INTO TODOS (title, desc) VALUES('" + this.title + "','" + this.desc + "')";
			int result = 0; 
			try {
				result = mObjContext.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return result; 
		} 
		public void Update() {
			
		} 
		
		public void DeleteTodo() {
			
		} 
	} 
	private Context mObjContext; 
	
	public TodosBusiness() {
		mObjContext = Context.getInstance(); 
	} 
	
	public boolean InsertTodo(String title, String desc) {
		todo t = new todo(title, desc);
		int result = t.InsertToDb(); 
		return (result != 0); 
	} 
	
	public todo GetTodoById(int id) {
		String sql = "SELECT * FROM TODOS WHERE id=" + id; 
		Object[] result;
		todo t = null; 
		try {
			result = mObjContext.executeQuery(sql);  
			for (int i=0; i<result.length; i++) {
				Object[] a = (Object[]) result[i];
				a[0].toString(); 
				// t = new todo();  
			} 
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return t; 
	} 
} 
