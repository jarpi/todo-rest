package com.blackhole.Model;

public class Request {

	private String verb; 
	private String path; 
	
	// Parse an HTTPRequest 
	public Request(String request) {
		// TODO: Parse request and fill values 
	} 
	
	public String GetPath() {
		return this.path; 
	}
	
	public String GetVerb() {
		return this.verb; 
	} 
	
}
