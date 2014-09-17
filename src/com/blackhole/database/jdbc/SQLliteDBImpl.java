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
			this.mCon = null; 
		} 
	} 
	
	public static SQLliteDBImpl getInstance() { 
		if (SQLliteDBImpl.mInstance == null) {
			SQLliteDBImpl.mInstance = new SQLliteDBImpl(); 
		} 
		if (SQLliteDBImpl.mInstance.mCon == null){
			return null; 
		} else {
			return SQLliteDBImpl.mInstance;
		}  
	} 
	
	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
	    this.mCon = DriverManager.getConnection("jdbc:sqlite:/tmp/todos.db"); 
	    return this.mCon;
	} 
	
	public Object[] executeQuery(String query, Object[] params) throws SQLException { 
		ResultSet result = null;  
		ArrayList<Object[]> resultObj = new ArrayList<Object[]>();   
		PreparedStatement stmt = this.mCon.prepareStatement(query);
		stmt = replaceQueryValuesWithParams(stmt, params); 
		// Call to stmt.executeUpdate(query) will override prepared statement 
		result = stmt.executeQuery(); 
		ResultSetMetaData rsmd = result.getMetaData(); 
		int columnCount = rsmd.getColumnCount(); 
		while (result.next()) { 
			Object[] objTmp = new Object[columnCount]; 
			for (int i=0; i<columnCount; i++) {
				objTmp[i] = result.getObject(i+1); 
			} 
			resultObj.add(objTmp); 
		} 
		return resultObj.toArray();  
	} 
	
	public Object[] executeQuery(String query) throws SQLException { 
		ResultSet result = null;  
		ArrayList<Object[]> resultObj = new ArrayList<Object[]>();   
		PreparedStatement stmt = this.mCon.prepareStatement(query); 
		// Call to stmt.executeUpdate(query) will override prepared statement 
		result = stmt.executeQuery(); 
		ResultSetMetaData rsmd = result.getMetaData(); 
		int columnCount = rsmd.getColumnCount(); 
		while (result.next()) { 
			Object[] objTmp = new Object[columnCount]; 
			for (int i=0; i<columnCount; i++) {
				objTmp[i] = result.getObject(i+1); 
			} 
			resultObj.add(objTmp); 
		} 
		return resultObj.toArray();  
	} 
	
	public int executeUpdate(String query, Object[] params) throws SQLException { 
		int result = 0; 
		PreparedStatement stmt = this.mCon.prepareStatement(query);
		stmt = replaceQueryValuesWithParams(stmt, params); 
		// Call to stmt.executeUpdate(query) will override prepared statement 
        result = stmt.executeUpdate(); 
		return result; 
	} 
	
	public int executeUpdate(String query) throws SQLException { 
		PreparedStatement stmt = this.mCon.prepareStatement(query); 
		// Call to stmt.executeUpdate(query) will override prepared statement 
	    int result = stmt.executeUpdate();  
		return result; 
	} 
	
	private boolean tableExists(String tableName) { 
		boolean result = false; 
		if (tableName == null || this.mCon == null) 
	    {
	        return false;
	    } 
		Statement stmt;
		String testExistsTableSql = "SELECT 1 FROM sqlite_master WHERE type=\"table\" AND name=\""+ tableName.toUpperCase() + "\""; 
		try { 
			stmt = this.mCon.createStatement(); 
			ResultSet rs = stmt.executeQuery(testExistsTableSql); 
			result = rs.next();  
			rs.close(); 
			stmt.close();  
		} catch (SQLException e) {
			e.printStackTrace(); 
		} 
		return result; 
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
