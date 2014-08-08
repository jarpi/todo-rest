package com.blackhole.Servlet;

import javax.servlet.http.HttpServletRequest;

public class Request {

	private String mVerb; 
	private String mPath; 
	private HttpServletRequest mRequest; 
	// Parse an HTTPRequest 
	public Request(HttpServletRequest request) {
		// TODO: Parse request and fill values 
		this.mRequest = request; 
		this.mPath = this.mRequest.getRequestURI().substring(this.mRequest.getContextPath().length()).substring(this.mRequest.getServletPath().length());
		this.mVerb = this.mRequest.getMethod(); 
	} 
	
	public String getPath() {
		return this.mPath; 
	}
	
	public String getVerb() {
		return this.mVerb; 
	} 
	
}
