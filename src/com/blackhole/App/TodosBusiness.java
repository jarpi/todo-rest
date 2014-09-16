package com.blackhole.App;

import java.sql.SQLException;
import java.util.ArrayList;

public class TodosBusiness { 
	// Inner class todo model representation  
	public class todo { 
		// Properties of inner class 
		private int id = 0;  
		private String title, desc; 
		private String[] testArr = {"ttt","ttt","ttt"}; 
		// Constructor by simple strings 
		public todo(String title, String desc) {this.title = title; this.desc = desc;} 
		// Constructor by array of objects (obj[0] == title, ...) 
		public todo(Object[] todo) {this.id = (int) todo[0]; this.title = (String) todo[1]; this.desc = (String) todo[2];}
		// Methods for accesing properties 
		public int getId() {return this.id;} 
		public String getTitle() {return this.title;} 
		public String getDesc()  {return this.desc;} 
		public String toString() {return this.id + " " + this.title.toString() + " " + this.desc.toString();} 
		public void setTitle(String title) {this.title = title;} 
		public void setDesc(String desc) {this.desc = desc;} 
		// CRUD methods 
		public int InsertTodoToDb() throws SQLException {
			String sql = "INSERT INTO TODOS (title, desc) VALUES('" + this.title + "','" + this.desc + "')";
			int result = 0; 
			result = Context.mDBConnection.executeUpdate(sql); 
			return result; 
		} 
		public int UpdateTodo() throws SQLException { 
			String sql = "UPDATE TODO SET title=?, desc=?, WHERE id=?";  
			ArrayList<Object> objParams = new ArrayList<Object>(); 
			objParams.add(this.id); 
			objParams.add(this.title); 
			objParams.add(this.desc); 
			int result = 0; 
			result = Context.mDBConnection.executeUpdate(sql, objParams.toArray(new Object[]{}));
			return result; 
		} 
		public int DeleteTodo() throws SQLException {
			String sql = "DELETE FROM todos WHERE id=?"; 
			ArrayList<Object> objParams = new ArrayList<Object>(); 
			objParams.add(this.id);  
			int result = 0; 
			result = Context.mDBConnection.executeUpdate(sql, objParams.toArray(new Object[]{}));
			return result; 
		} 
	} 
	
	public TodosBusiness() {} 
	
	public boolean InsertTodo(String title, String desc) {
		todo t = new todo(title, desc);
		int result = 0;
		try {
			result = t.InsertTodoToDb(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return (result != 0); 
	} 
	
	public todo GetTodoById(int id) {
 		String sql = "SELECT * FROM TODOS WHERE id=?"; 
 		ArrayList<Object> objParams = new ArrayList<Object>();
 		objParams.add(id); 
		Object[] result;
		todo t = null; 
		try { 
			result = Context.mDBConnection.executeQuery(sql, objParams.toArray(new Object[]{}));  
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
			result = Context.mDBConnection.executeQuery(sql);  
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
	
	public todo[] GetTodosByLimit(int rowStart, int rowOffset) { 
		ArrayList<todo> todos = new ArrayList<todo>(); 
		String sql = "SELECT * FROM TODOS LIMIT ?,?";  
		ArrayList<Object> objParams = new ArrayList<Object>(); 
		objParams.add(rowStart); 
		objParams.add(rowOffset); 
		Object[] result;
		todo t = null; 
		try {
			result = Context.mDBConnection.executeQuery(sql, objParams.toArray(new Object[]{}));  
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
	
	public boolean DeleteTodoById(int id) {
		todo t = this.GetTodoById(id); 
		int result = 0; 
		try {
			result = t.DeleteTodo();
		} catch (SQLException e) { 
			e.printStackTrace();
		}  
		return (result != 0); 
	} 
	
	public boolean UpdateTodo(int id, String title, String desc) {
		todo t = this.GetTodoById(id); 
		t.setTitle(title); 
		t.setDesc(desc); 
		int result = 0; 
		try {
			result = t.UpdateTodo();
		} catch (SQLException e) { 
			e.printStackTrace();
		}  
		return (result != 0); 
	} 
} 
