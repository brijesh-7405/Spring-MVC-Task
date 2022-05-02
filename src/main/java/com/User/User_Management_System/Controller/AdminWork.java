package com.User.User_Management_System.Controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;

public class AdminWork extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(AdminWork.class.getName());
	//UserService Object creation
	private transient UserService userservice;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users;
		users = userservice.getUsers();                     //Calling a method who returns the all users list 
	 	request.setAttribute("UsersList",users);			//Storing users list into request Attribute
	 	LOG.info("userlist updated");
	 	RequestDispatcher rf=request.getRequestDispatcher("adminDashBoard.jsp");         
	 	rf.forward(request, response);
	}

}
