package com.User.User_Management_System.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;

public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(UserDetails.class.getName());

	private transient UserService userservice;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		User user = (User) session.getAttribute("USER");
		RequestDispatcher rf=request.getRequestDispatcher("registration.jsp");
		if(user.getRole().equals("user"))
		{
			request.setAttribute("user",user);					//if role is user then store the user from session in request attribute
			LOG.info("Updated User stored in Request");
		 	rf.forward(request, response);
		}
		else
		{
			String uid = request.getParameter("userid");
			int userid = Integer.parseInt(uid);
			User usr = userservice.getUserDetails(userid);      //else get the particular user from the user id which edit part is on Admin hand
			request.setAttribute("user", usr);
			LOG.info("Updated User stored in Request");
			rf.forward(request, response);
		}
		
	}

}
