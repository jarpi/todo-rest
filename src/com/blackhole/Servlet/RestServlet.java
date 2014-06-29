package com.blackhole.Servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackhole.App.NotesBusinessLogic;
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
		Context c = new Context(); 
		Logger l = c.getLoggingInstance(); 
		l.log(Level.INFO, "Starting GET Request|Time: " + "|IP:" + "|Request: "); 
		String requestedPath = req.getRequestURI().substring(req.getContextPath().length()).substring(req.getServletPath().length()); 
        new Runner(NotesBusinessLogic.class, requestedPath, "GET"); 
        c.dispose(); 
	} 
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		Context c = new Context(); 
		Logger l = c.getLoggingInstance(); 
		l.log(Level.INFO, "Starting POST Request|Time: " + "|IP:" + "|Request: "); 
		String requestedPath = req.getRequestURI().substring(req.getContextPath().length()).substring(req.getServletPath().length()); 
        new Runner(NotesBusinessLogic.class, requestedPath, "POST");
        c.dispose(); 
	} 
	
	protected void doUpdate(HttpServletRequest req, HttpServletResponse resp) {}  
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {} 
} 
