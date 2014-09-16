package com.blackhole.database.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.ArrayList;

public class SQLliteDBImpl {  
	
	private static SQLliteDBImpl mInstance; 
	private Connection mCon; 
	
	public SQLliteDBImpl() {
		try {
			this.mCon = this.getConnection(); 
			if (!this.tableExists("todos")) { 
				String sql = "CREATE TABLE TODOS " +
		                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + 
		                " TITLE TEXT NOT NULL, " + 
		                " DESC TEXT NULL)"; 
				this.executeUpdate(sql);  
			} 
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	} 
	
	public static SQLliteDBImpl getInstance() { 
		if (SQLliteDBImpl.mInstance == null) {
			SQLliteDBImpl.mInstance = new SQLliteDBImpl(); 
		} 
		return SQLliteDBImpl.mInstance;  
	} 
	
	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
	    this.mCon = DriverManager.getConnection("jdbc:sqlite:/tmp/todos.db"); 
	    return this.mCon;
	} 
	
	public Object[] executeQuery(String query, Object[] params) throws SQLException { 
		ResultSet result = null;  
		ArrayList<Object[]> resultObj = new ArrayList<Object[]>();   
		try { 
			PreparedStatement stmt = this.mCon.prepareStatement(query);
			stmt = replaceQueryValuesWithParams(stmt, params); 
			result = stmt.executeQuery(query); 
			ResultSetMetaData rsmd = result.getMetaData(); 
			int columnCount = rsmd.getColumnCount(); 
			while (result.next()) { 
				Object[] objTmp = new Object[columnCount]; 
				for (int i=0; i<columnCount; i++) {
					objTmp[i] = result.getObject(i+1); 
				} 
				resultObj.add(objTmp); 
			} 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		return resultObj.toArray();  
	} 
	
	public Object[] executeQuery(String query) throws SQLException { 
		ResultSet result = null;  
		ArrayList<Object[]> resultObj = new ArrayList<Object[]>();   
		try (PreparedStatement stmt = this.mCon.prepareStatement(query);){ 
			result = stmt.executeQuery(query); 
			ResultSetMetaData rsmd = result.getMetaData(); 
			int columnCount = rsmd.getColumnCount(); 
			while (result.next()) { 
				Object[] objTmp = new Object[columnCount]; 
				for (int i=0; i<columnCount; i++) {
					objTmp[i] = result.getObject(i+1); 
				} 
				resultObj.add(objTmp); 
			} 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		return resultObj.toArray();  
	} 
	
	public int executeUpdate(String query, Object[] params) throws SQLException { 
		int result = 0; 
		try { 
			PreparedStatement stmt = this.mCon.prepareStatement(query);
			stmt = replaceQueryValuesWithParams(stmt, params); 
	        result = stmt.executeUpdate(query); 
		} catch (SQLException e ) {
	        e.printStackTrace(); 
	    }  
		return result; 
	} 
	
	public int executeUpdate(String query) throws SQLException { 
		int result = 0; 
		try (PreparedStatement stmt = this.mCon.prepareStatement(query);){   
	        result = stmt.executeUpdate(query); 
		} catch (SQLException e ) {
	        e.printStackTrace(); 
	    }  
		return result; 
	} 
	
	private boolean tableExists(String tableName) {
		if (tableName == null || this.mCon == null) 
	    {
	        return false;
	    } 
		Statement stmt;
		String testExistsTableSql = "SELECT 1 FROM sqlite_master WHERE type=\"table\" AND name=\""+ tableName.toUpperCase() + "\""; 
		try { 
			stmt = this.mCon.createStatement(); 
			ResultSet rs = stmt.executeQuery(testExistsTableSql); 
			rs.next(); 
			int result = rs.getInt(1);  
			if (result == 0) return false; 
			rs.close(); 
			stmt.close();  
			return true; 
		} catch (SQLException e) {
			e.printStackTrace(); 
		} 
		return false; 
	} 
	
	public Connection getDBConnection() {
		return this.mCon; 
	} 
	
	public PreparedStatement replaceQueryValuesWithParams(PreparedStatement stmt, Object[] params) throws SQLException {
		int paramNumberInQuery = 1; 
		for (Object objParam : params) {
			if (objParam.getClass() == Integer.class) {
				stmt.setInt(paramNumberInQuery, (int) objParam); 
			} else if (objParam.getClass() == String.class) {
				stmt.setString(paramNumberInQuery, (String) objParam);
			} else if (objParam.getClass() == Date.class) {
				stmt.setDate(paramNumberInQuery, (Date) objParam);
			} 
			paramNumberInQuery += 1; 
		} 
		return stmt; 
	} 
	
	public void dispose() throws SQLException {
		this.mCon.close(); 
		this.mCon = null; 
	}
}
