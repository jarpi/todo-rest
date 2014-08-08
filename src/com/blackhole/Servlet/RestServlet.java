package com.blackhole.Servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackhole.App.AppFacade;
import com.blackhole.App.Context;
import com.blackhole.RestRunner.Runner;

public class RestServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2258190026635506539L;

	public RestServlet(){}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		/* System.out.println(req.getPathInfo()); 
		String field = req.getParameter("field"); 
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("You entered into the text box."); 
        out.println("</body>");
        out.println("</html>"); */  
		/* REST runner takes 2 params as argument
		 *  1- Class that contains annotation implementation 
		 *  2- Class that contains annotation name 
		 *  TODO: 
		 *  - Send an arbitrary number of annotation classes 
		 *  - Scan automatically defined annotations  
		 */ 
		// Transform URI to Path.value param type from annotation 
		Context c = Context.getInstance();  
		c.logInfo("Starting GET Request|Time: " + "|IP:" + "|Request: "); 
		Request r = new Request(req);  
        new Runner(AppFacade.class, r.getPath(), r.getVerb()); 
	} 
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		Context c = Context.getInstance();  
		c.logInfo("Starting GET Request|Time: " + "|IP:" + "|Request: "); 
		Request r = new Request(req);  
        new Runner(AppFacade.class, r.getPath(), r.getVerb()); 
	} 
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		Context c = Context.getInstance();  
		c.logInfo("Starting GET Request|Time: " + "|IP:" + "|Request: "); 
		Request r = new Request(req);  
        new Runner(AppFacade.class, r.getPath(), r.getVerb()); 
	}  
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
		Context c = Context.getInstance();  
		c.logInfo("Starting GET Request|Time: " + "|IP:" + "|Request: "); 
		Request r = new Request(req);  
        new Runner(AppFacade.class, r.getPath(), r.getVerb()); 
	} 
} 
