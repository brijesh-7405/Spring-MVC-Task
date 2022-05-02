package com.User.User_Management_System.Controller;

import java.io.IOException;

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

public class UserData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(UserData.class.getName());

	private transient UserService userservice;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Enter in userdata servlet");
		HttpSession session=request.getSession(false);             //Getting session
		User user = (User) session.getAttribute("USER"); 			 //Getting session attribute
		if(user.getRole().equals("admin"))							//Check the role of the user if admin then redirect to his Servlet
		{
			LOG.info("Admin is in Session");
			response.sendRedirect("AdminWork");
		}
		else														//If the  role of the user is user then update the user details in the database and then stored that updated user in session  
		{
			user = userservice.checkUser(user.getEmail());
			session.setAttribute("USER", user);
			LOG.info("Updated User - stored in session");
			response.sendRedirect("userDashBoard.jsp");
		}
	}
}
