package com.blackhole.database.jdbc;

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
	    this.mCon = DriverManager.getConnection("jdbc:sqlite:todos.db"); 
	    return this.mCon;
	} 
	
	public Object[] executeQuery(String query) throws SQLException { 
		ResultSet result = null;  
		ArrayList<Object[]> resultObj = new ArrayList<Object[]>();   
		try (Statement stmt = this.mCon.createStatement();) {
			result = stmt.executeQuery(query); 
	        /* while (result.next()) {
	            String coffeeName = result.getString("COF_NAME");
	            int supplierID = result.getInt("SUP_ID");
	            float price = result.getFloat("PRICE");
	            int sales = result.getInt("SALES");
	            int total = result.getInt("TOTAL");
	            System.out.println(coffeeName + "\t" + supplierID +
	                               "\t" + price + "\t" + sales +
	                               "\t" + total);
	        } */ 
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
	
	public int executeUpdate(String sql) throws SQLException {
		int result = 0; 
		try (Statement stmt = this.mCon.createStatement();) {
	        result = stmt.executeUpdate(sql); 
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
	
	public void dispose() throws SQLException {
		this.mCon.close(); 
		this.mCon = null; 
	}
}
