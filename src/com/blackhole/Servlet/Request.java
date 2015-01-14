package com.blackhole.Servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class Request {

	private String mVerb; 
	private String mPath; 
	private HttpServletRequest mRequest; 
	// Parse an HTTPRequest 
	public Request(HttpServletRequest request) {
		// TODO: Parse request and fill values 
		this.mRequest = request; 
		try {
			this.mPath = URLDecoder.decode(this.mRequest.getRequestURI().substring(this.mRequest.getContextPath().length()).substring(this.mRequest.getServletPath().length()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(); 
		} 
		this.mVerb = this.mRequest.getMethod(); 
	} 
	
	public String getPath() {
		return this.mPath; 
	}
	
	public String getVerb() {
		return this.mVerb; 
	} 
	
}
