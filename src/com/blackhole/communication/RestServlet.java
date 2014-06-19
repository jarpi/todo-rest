package com.blackhole.communication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackhole.Model.BusinessLogic;
import com.blackhole.RestRunner.Runner;
import com.blackhole.RestRunner.Annotations.Path;

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
		// Transform URI to Path.value param annotation 
		String requestedPath = req.getRequestURI().substring(req.getContextPath().length()).substring(req.getServletPath().length()); 
        new Runner(BusinessLogic.class, requestedPath, "GET"); 
	} 
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException { 
		String requestedPath = req.getRequestURI().substring(req.getContextPath().length()).substring(req.getServletPath().length()); 
        new Runner(BusinessLogic.class, requestedPath, "POST");    
	} 
} 