package com.User.User_Management_System.Controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;


public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(DeleteUser.class.getName());
	private transient   UserService userservice;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Enter in Delete User servlet");
		response.setContentType("text/html");
		String uid=request.getParameter("userid");     //Parsing the String into integer  the userid 
		int userid = Integer.parseInt(uid);
		userservice.deleteUser(userid);               //Calling a method to delete the user from the database
		response.sendRedirect("AdminWork");
		LOG.debug("User deleted");
	}

}
