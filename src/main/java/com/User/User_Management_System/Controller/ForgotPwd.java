package com.User.User_Management_System.Controller;

import java.io.IOException;

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

public class ForgotPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(ForgotPwd.class.getName());
	private  transient  UserService userservice;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String email=request.getParameter("email");
		String birthdate=request.getParameter("birthdate");
		String ans1=request.getParameter("q1");
		String ans2=request.getParameter("q2");
		User user = userservice.checkUser(email);			//check the user is present in database or not
		RequestDispatcher r=request.getRequestDispatcher("forgotpwd.jsp");
		if(user!=null)															//check the user is present in database or not
		{
				if(birthdate.equals(user.getDateofbirth()))
				{
					if(ans1.equals(user.getAnswer1()) && ans2.equals(user.getAnswer2()))
					{
						LOG.debug("All details are correct");
						RequestDispatcher rf=request.getRequestDispatcher("resetpwd.jsp");
						request.setAttribute("email",user.getEmail());
						rf.forward(request, response);
					}
					else
					{
						LOG.error("Security Answers are wrong");
						request.setAttribute("message","*Security Answers are wrong");
						r.forward(request, response);
					}
				}
				else
				{
					LOG.error("Invalid BirthDate");
					request.setAttribute("message","*Invalid BirthDate");
					r.forward(request, response);
				}
		}
		else
		{
			LOG.error("Invalid user");
			request.setAttribute("message","*Invalid User");
			r.forward(request, response);
		}
	}
}
