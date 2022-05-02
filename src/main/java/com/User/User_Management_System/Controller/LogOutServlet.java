package com.User.User_Management_System.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(LogOutServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BasicConfigurator.configure();
		response.setContentType("text/html");  
        PrintWriter out=response.getWriter();
        
        
        HttpSession session=request.getSession(false); 
        if(session!=null)
        {
	         session.invalidate(); 								//Session close or Session Invalidate after logout user
        }   
        LOG.debug("Successfully logged out");
        response.sendRedirect("index.jsp");
        out.close(); 
	}

}
