package com.blackhole.App;

import javax.servlet.http.HttpServletRequest;

public class Request {

	private String verb; 
	private String path; 
	private HttpServletRequest request; 
	// Parse an HTTPRequest 
	public Request(HttpServletRequest request) {
		// TODO: Parse request and fill values 
		this.request = request; 
		this.path = this.request.getRequestURI().substring(this.request.getContextPath().length()).substring(this.request.getServletPath().length());
		this.verb = this.request.getMethod(); 
	} 
	
	public String getPath() {
		return this.path; 
	}
	
	public String getVerb() {
		return this.verb; 
	} 
	
}
