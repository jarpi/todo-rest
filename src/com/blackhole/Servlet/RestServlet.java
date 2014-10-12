package com.blackhole.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blackhole.App.AppFacade;
import com.blackhole.App.Context; 
import com.blackhole.App.TodosBusiness;
import com.blackhole.App.TodosBusiness.todo;
import com.blackhole.RestRunner.Runner;

public class RestServlet extends HttpServlet{

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
		this.parseRequestAndProduceResponse(req, resp);
	} 
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
		this.parseRequestAndProduceResponse(req, resp);
	} 
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		this.parseRequestAndProduceResponse(req, resp); 
	}  
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
		this.parseRequestAndProduceResponse(req, resp); 
	} 
	
	private void parseRequestAndProduceResponse(HttpServletRequest req, HttpServletResponse resp) {
		Object result = null; 
		/* Context c = Context.getInstance(); 
		TodosBusiness tb = new TodosBusiness(); 
		todo t = tb.new todo("AAA", "BBB"); 
		todo t1 = tb.new todo("CCC", "DDD");
		todo t2 = tb.new todo("EEE", "FFF");  
		try {
			c.mObjUtilsInstance.ObjectToJSON(new todo[]{t,t1,t2});
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */ 
		Context c = new Context(); 
		c.logInfo("Starting GET Request|Time: " + "|IP:" + "|Request: "); 
		Request r = new Request(req);  
        Runner ru = new Runner(AppFacade.class, r.getPath(), r.getVerb(), new Object[]{c}); 
        try { 
        	result = ru.exploreAnnotatedClass();
        }  catch (Exception e) {
        	e.printStackTrace(); 
        } 
        if (result != null) {
			try {
				resp.getWriter().write(result.toString()); 
				resp.getWriter().flush(); 
				resp.getWriter().close(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
        } 
	} 
} 


