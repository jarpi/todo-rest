package com.blackhole.App;

import java.sql.SQLException;
import java.util.ArrayList;

public class TodosBusiness { 
	// Inner class model 
	class todo { 
		private int id = 0;  
		private String title, desc; 
		public todo(String title, String desc) {this.title = title; this.desc = desc;} 
		public todo(Object[] todo) {this.id = (int) todo[0]; this.title = (String) todo[1]; this.desc = (String) todo[2];}
		public todo(int id, String title, String desc) {}
		public int getId() {return this.id;} 
		public String getTitle() {return this.title;} 
		public String getDesc()  {return this.desc;} 
		public String toString() {return this.id + " " + this.title.toString() + " " + this.desc.toString();} 
		public int InsertToDb() throws SQLException {
			String sql = "INSERT INTO TODOS (title, desc) VALUES('" + this.title + "','" + this.desc + "')";
			int result = 0; 
			result = mObjContext.executeUpdate(sql); 
			return result; 
		} 
		public void Update() {} 
		public void DeleteTodo() {} 
	} 
	private Context mObjContext; 
	
	public TodosBusiness() {
		mObjContext = Context.getInstance(); 
	} 
	
	public boolean InsertTodo(String title, String desc) {
		todo t = new todo(title, desc);
		int result = 0;
		try {
			result = t.InsertToDb(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return (result != 0); 
	} 
	
	public todo GetTodoById(int id) {
 		String sql = "SELECT * FROM TODOS WHERE id=" + id; 
		Object[] result;
		todo t = null; 
		try {
			result = mObjContext.executeQuery(sql);  
			if (result.length > 0) {
				for (int i=0; i<result.length; i++) { 
					Object[] a = (Object[]) result[i];
					a[0].toString(); 
					t = new todo(a);  
				} 
			} 
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return t; 
	} 
	
	public todo[] GetTodos() {
		ArrayList<todo> todos = new ArrayList<todo>(); 
		String sql = "SELECT * FROM TODOS";  
		Object[] result;
		todo t = null; 
		try {
			result = mObjContext.executeQuery(sql);  
			if (result.length > 0) {
				for (int i=0; i<result.length; i++) { 
					Object[] row = (Object[]) result[i]; 
					t = new todo(row);   
					todos.add(t); 
				} 
			} 
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return todos.toArray(new todo[]{}); 
	} 
	
	public todo[] GetFilteredTodos(int rowStart, int rowsOffset) { 
		ArrayList<todo> todos = new ArrayList<todo>(); 
		String sql = "SELECT * FROM TODOS LIMIT " + rowStart + "," + rowsOffset;  
		Object[] result;
		todo t = null; 
		try {
			result = mObjContext.executeQuery(sql);  
			if (result.length > 0) {
				for (int i=0; i<result.length; i++) { 
					Object[] row = (Object[]) result[i]; 
					t = new todo(row);   
					todos.add(t); 
				} 
			} 
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return todos.toArray(new todo[]{}); 
	}
} 
