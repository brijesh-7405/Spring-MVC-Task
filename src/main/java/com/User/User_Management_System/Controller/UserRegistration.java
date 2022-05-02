package com.User.User_Management_System.Controller;

import java.io.IOException;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.Service.UserAddressService;
import com.User.User_Management_System.Service.UserAddressServiceImpl;
import com.User.User_Management_System.Service.UserImageService;
import com.User.User_Management_System.Service.UserImageServiceImpl;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;
@MultipartConfig
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(UserRegistration.class.getName());  
	private transient UserService userservice;
	private transient UserAddressService userAddressService;
	private transient UserImageService userImageService;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
		userAddressService = new UserAddressServiceImpl();
		userImageService = new UserImageServiceImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String email=request.getParameter("email"); 
		if(userservice.userExist(email))
		{
			LOG.info("*Email already exist");
		}
		else
		{
		   try {
				String pwd = (String) request.getAttribute("password");
				String fname=request.getParameter("firstname");  
				String lname=request.getParameter("lastname"); 
				String phone=request.getParameter("phone");
				String birthdate=request.getParameter("birthdate");
				String ans1=request.getParameter("q1");
				String ans2=request.getParameter("q2");
				String gender=request.getParameter("Gender");
				String address1[]=request.getParameterValues("address1");
				String address2[]=request.getParameterValues("address2");
				String[] pincode=request.getParameterValues("pincode");
				String[] city=request.getParameterValues("city");
				String[] state=request.getParameterValues("state");
				String[] country=request.getParameterValues("country");
				String lang[]=request.getParameterValues("lang");
				
				String language="";
				StringBuffer buf = new StringBuffer();
				for(int i=0;i< lang.length;i++){
					buf.append(" "+lang[i]);
				}
				language=buf.toString();
				long number = Long.parseLong(phone);
				User user = new User();
				user.setFirstname(fname);
				user.setLastname(lname);
				user.setEmail(email);
				user.setPhone(number);
				user.setPassword(pwd);
				user.setDateofbirth(birthdate);
				user.setAnswer1(ans1);
				user.setAnswer2(ans2);
				user.setGender(gender);
				user.setLanguage(language);
				
				userservice.registerUser(user);    //Register user data in user database
				LOG.debug("User data Added");
				int userid=userservice.getUser(email);    //get user id which was just added into database
				UserAddress useraddress;
				for(int i=0;i<address1.length;i++)
				{
						useraddress= new UserAddress();
						useraddress.setUserid(userid);
						useraddress.setAdd1(address1[i]);
						useraddress.setAdd2(address2[i]);
						useraddress.setPincode(pincode[i]);
						useraddress.setCity(city[i]);
						useraddress.setState(state[i]);
						useraddress.setCountry(country[i]);
						userAddressService.addUserAddress(useraddress);      //Add the user multiple address in address table
				}
				LOG.debug("User Addresses Added");
				@SuppressWarnings({ "unchecked", "rawtypes" })
				ArrayList<Part> fileParts = (ArrayList) request.getParts().stream().filter(new Predicate<Part>() {
					public boolean test(Part part) {
					return "image[]".equals(part.getName()) && part.getSize() > 0;       //it scan all the fields of form but only return the images added into the uploader
				}
				}).collect(Collectors.toList()); 
			    InputStream inputStream = null;
				UserImage userimg=null;
		        for (Part filePart : fileParts) {
		            if (filePart != null && filePart.getSize() != 0) {
		            	 userimg = new UserImage();
		                inputStream = filePart.getInputStream();
		                userimg.setUserid(userid);
		                userimg.setImage(inputStream);
		                userImageService.addUserImg(userimg);      //Add the user multiple Images in image table
		            }
		            LOG.debug("User Images Added in database");
		        }
		        LOG.debug("User Images Added");
		        LOG.info("Registration Successfully");
		        HttpSession session=request.getSession(false);
		        if(session.getAttribute("USER") == null)           //Check if session has no attribute the redirect it to login page
		        {
		        	RequestDispatcher rf=request.getRequestDispatcher("index.jsp");
		        	rf.include(request, response);
		        }
		        else
		        {
		        	response.sendRedirect("AdminWork");       //else if admin add new user from its login side then redirect it to admin panel
		        }
			 }
			 catch(Exception e)
			 {
				 LOG.fatal(e);
				 LOG.error("Registration fails");
			 }
				
			}
	}

}
