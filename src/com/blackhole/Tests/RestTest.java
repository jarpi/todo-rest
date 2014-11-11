package com.blackhole.Tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class RestTest {
	private final String USER_AGENT = "Mozilla/5.0";
	
	@Test 
	public void AddNote() {
		String url = "http://192.168.1.100:8080/todo-rest/rest/addNote/test_note_title/test_note_desc";  
		try {
			String result = sendGet(url); 
			assertEquals(result,"{\"result\":\"true\"}");  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test 
	public void GetNotesEmpty() { 
		String url = "http://192.168.1.100:8080/todo-rest/rest/getNotes"; 
		try {
			String result = sendGet(url); 
			assertEquals(result,"");  
		} catch (Exception e) {
			e.printStackTrace();
		}  
	} 
	@Test 
	public void GetNoteById() {fail("Not implemented");} 
	@Test 
	public void GetNotesByFilter() {fail("Not implemented");} 
	@Test 
	public void UpdateNoteById() {fail("Not implemented");}
	@Test 
	public void DeleteNoteById() {fail("Not implemented");} 
	@Test 
	public void IsPlayerRunning() {fail("Not implemented");} 
	@Test 
	public void StartPlayer() {fail("Not implemented");}
	@Test 
	public void StopPlayer() {fail("Not implemented");} 
	@Test 
	public void AddVolume() {fail("Not implemented");}
	@Test 
	public void DelVolume() {fail("Not implemented");} 
	
	private String sendGet(String url) throws Exception {
		URL obj = new URL(url); 
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT); 
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		//print result
		return response.toString(); 
 
	}
	
	private String sendPost(String url) throws Exception { 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "es-ES,en;q=0.5");
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		} 
		in.close();
		//print result
		return response.toString();  
	} 
}
