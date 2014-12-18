package com.blackhole.App.Todos;

import java.sql.SQLException;
import java.util.ArrayList;

import com.blackhole.App.Context;

public class TodosBusiness { 
	Context mObjContext = null; 
	public TodosBusiness(Context c) {this.mObjContext = c;} 
	
	public int InsertTodo(String title, String desc) {
		todo t = new todo(mObjContext, title, desc);
		int result = 0;
		try {
			result = t.InsertTodoToDb(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result; 
	} 
	
	public todo GetTodoById(int id) {
 		String sql = "SELECT * FROM TODOS WHERE id=?"; 
 		ArrayList<Object> objParams = new ArrayList<Object>();
 		objParams.add(id); 
		Object[] result;
		todo t = null; 
		try { 
			result = mObjContext.mDBConnection.executeQuery(sql, objParams.toArray(new Object[]{}));  
			if (result.length > 0) { 
				for (int i=0; i<result.length; i++) { 
					// a == row 
					Object[] a = (Object[]) result[i];
					// Create todo from row 
					t = new todo(mObjContext,a);  
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
			result = mObjContext.mDBConnection.executeQuery(sql);  
			if (result.length > 0) {
				for (int i=0; i<result.length; i++) { 
					Object[] row = (Object[]) result[i]; 
					t = new todo(mObjContext,row);   
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
			result = mObjContext.mDBConnection.executeQuery(sql, objParams.toArray(new Object[]{}));  
			if (result.length > 0) { 
				for (int i=0; i<result.length; i++) { 
					Object[] row = (Object[]) result[i]; 
					t = new todo(mObjContext, row);   
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
		int result = 0; 
		if (t!=null) {
			t.setTitle(title); 
			t.setDesc(desc);  
			try {
				result = t.UpdateTodo();
			} catch (SQLException e) { 
				e.printStackTrace();
			}  
		} 
		return (result != 0); 
	} 
} 
