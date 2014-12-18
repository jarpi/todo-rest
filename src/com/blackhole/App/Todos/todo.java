package com.blackhole.App.Todos;

import java.sql.SQLException;
import java.util.ArrayList;

import com.blackhole.App.Context;

// DAO 
public class todo { 
	// Properties of inner class 
	private Context mObjContext = null; 
	private int id = 0;  
	private String title, desc;   
	// Inner class constructor, todo: this must be converted to a generic data container 
	public todo(Context c, String title, String desc) {this.mObjContext = c; this.title = title; this.desc = desc;} 
	// Constructor by array of objects (obj[0] == title, ...) 
	public todo(Context c, Object[] todo) {this.mObjContext = c; this.id = (int) todo[0]; this.title = (String) todo[1]; this.desc = (String) todo[2];}
	// Methods for accesing properties 
	public int getId() {return this.id;} 
	public String getTitle() {return this.title;} 
	public String getDesc()  {return this.desc;} 
	public String toString() {return this.id + " " + this.title.toString() + " " + this.desc.toString();} 
	public void setTitle(String title) {this.title = title;} 
	public void setDesc(String desc) {this.desc = desc;} 
	// CRUD methods 
	public int InsertTodoToDb() throws SQLException {
		String sql = "INSERT INTO todos (title, desc) VALUES(?,?)"; 
		ArrayList<Object> objParams = new ArrayList<Object>(); 
		objParams.add(this.title); 
		objParams.add(this.desc);
		int result = 0; 
		result = this.mObjContext.mDBConnection.executeUpdate(sql,objParams.toArray(new Object[]{}));
		if (result != 0) return (int) this.mObjContext.mDBConnection.LastInsertId(); 
		return result; 
	} 
	public int UpdateTodo() throws SQLException { 
		String sql = "UPDATE todos SET title=?, desc=? WHERE id=?";  
		ArrayList<Object> objParams = new ArrayList<Object>(); 
		objParams.add(this.id); 
		objParams.add(this.title); 
		objParams.add(this.desc); 
		int result = 0; 
		result = this.mObjContext.mDBConnection.executeUpdate(sql, objParams.toArray(new Object[]{}));
		return result; 
	} 
	public int DeleteTodo() throws SQLException {
		String sql = "DELETE FROM todos WHERE id=?"; 
		ArrayList<Object> objParams = new ArrayList<Object>(); 
		objParams.add(this.id);  
		int result = 0; 
		result = this.mObjContext.mDBConnection.executeUpdate(sql, objParams.toArray(new Object[]{}));
		return result; 
	} 
} 