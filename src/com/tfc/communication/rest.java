package com.tfc.communication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tfc.rest_implementation.Runner;

public class rest extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2258190026635506539L;

	public rest(){}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		System.out.println(req.getPathInfo()); 
		String field = req.getParameter("field"); 
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("You entered into the text box."); 
        out.println("</body>");
        out.println("</html>");
        Runner r = new Runner(AnnotationTest.class,CanRun.class); 
	} 
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		System.out.println(req.getPathInfo()); 
	}
} 