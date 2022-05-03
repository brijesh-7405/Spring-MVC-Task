package com.User.User_Management_System.Filter;

import java.io.IOException;

import java.util.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;
import com.User.User_Management_System.UtilityClass.CheckValidation;
@MultipartConfig
//@WebFilter(urlPatterns={"/UserRegistration"})
public class Validation implements Filter {
	static final Logger LOG = LogManager.getLogger(Validation.class.getName());
	private transient CheckValidation  val;
	private transient UserService userservice;
	public void init(FilterConfig fConfig) throws ServletException {
		val = new CheckValidation();
		userservice = new UserServiceImpl();
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.info("Validation filter call");
		response.setContentType("text/html");
		String fname=request.getParameter("firstname");  
		String lname=request.getParameter("lastname");
		String email=request.getParameter("email"); 
		String phone=request.getParameter("phone");
		String pwd=request.getParameter("password");
		String repwd=request.getParameter("repass");
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
		String language[]=request.getParameterValues("lang");
    
		User user = new User();
		user.setFirstname(fname);
		user.setLastname(lname);
		user.setEmail(email);
		if(val.validateNumber(phone))
		{
			request.setAttribute("phonenumber", phone);
		}
		else {
		 user.setPhone(Long.parseLong(phone));
		}
		user.setPassword(pwd);
		user.setDateofbirth(birthdate);
		user.setAnswer1(ans1);
		user.setAnswer2(ans2);
		user.setGender(gender);
		String lang="";
		if(language!=null)
		{
			StringBuffer buf = new StringBuffer();
			for(int i=0;i< language.length;i++){
				buf.append(language[i]);
			}
			lang=buf.toString();
		}
		user.setLanguage(lang);
		UserAddress useraddress;
		ArrayList<UserAddress> list = new ArrayList<UserAddress>();
		if(address1!=null)
		{
			for(int i=0;i<address1.length;i++)
			{
					useraddress= new UserAddress();
					useraddress.setAdd1(address1[i]);
					useraddress.setAdd2(address2[i]);
					useraddress.setPincode(pincode[i]);
					useraddress.setCity(city[i]);
					useraddress.setState(state[i]);
					useraddress.setCountry(country[i]);
					list.add(useraddress);
			}
		}
		user.setAddress(list);
		RequestDispatcher rd=request.getRequestDispatcher("registration.jsp"); 
		request.setAttribute("formdata", user);
		
		boolean validate=true;
		if(fname.equals("")||pwd.equals("")||lname.equals("")||email.equals("")||phone.equals("")||repwd.equals("")||birthdate.equals("")||ans1.equals("")||ans2.equals("")||gender.equals(""))
		{
			request.setAttribute("message","*All Feilds are Required");
			rd.forward(request, response);
			validate=false;
		}
		else if(address1==null||address2==null||pincode==null||city==null||state==null||country==null||language==null)
		{
			request.setAttribute("message","*All Feilds are Required");
			rd.forward(request, response);
			validate=false;
		}
		else if(val.validatename(fname))
		{
			request.setAttribute("message","*Only Alphabets are Allowed in FirstName.");
			rd.forward(request, response);
			validate=false;
		}
		else if(val.validatename(lname))
		{
			request.setAttribute("message","*Only Alphabets are Allowed in LastName.");
			rd.forward(request, response);
			validate=false;
		}
		else if(val.validatepwd(pwd))
		{
			request.setAttribute("message","*Please Choose Strong Password.");
			rd.forward(request, response);
			validate=false;
		}
		else if(!pwd.equals(repwd))
		{
			request.setAttribute("message","*Confirm password Should be same as Password.");
			rd.forward(request, response);
			validate=false;
		}
		else if(val.validateNumber(phone))
		{
			request.setAttribute("message","*Only Numbers are Allowed in Phone.");
			rd.forward(request, response);
			validate=false;
		}
		else if(phone.length()<10)
		{
			request.setAttribute("message","*Number not less than 10 Digits.");
			rd.forward(request, response);
			validate=false;
		}
		else if(val.validatemail(email))
		{
			request.setAttribute("message","*Please Enter Valid Mail-Id.");
			rd.forward(request, response);
			validate=false;
		}
		else if(userservice.userExist(email))
		{
			request.setAttribute("message","*Email Already exist.");
			rd.forward(request, response);
			validate=false;
		}
		if(pincode!=null)
		{
			for(int i=0;i<pincode.length;i++)
			{
				if(address1[i].equals("") || address2[i].equals("") || city[i].equals("") || state[i].equals("") || country[i].equals("") ||  pincode[i].equals(""))
				{
					request.setAttribute("message","*All Address Fields are Required.");
					rd.forward(request, response);
					validate=false;
					break;
				}
				else if(val.validateNumber(pincode[i]))
				{
					request.setAttribute("message","*Only Numbers are Allowed in pincode.");
					rd.forward(request, response);
					validate=false;
					break;
				}
				else if(pincode[i].length()>6)
				{
					request.setAttribute("message","*Pincode not greater than 6 Digits.");
					rd.forward(request, response);
					validate=false;
					break;
				}
				else if(address1[i].length()>255 || address2[i].length()>255)
				{
					request.setAttribute("message","*Address length exceeded.");
					rd.forward(request, response);
					validate=false;
					break;
				}
			}
		}
		
		
		if(validate==true)
		{
			LOG.info("Validation SuccessFull");
			chain.doFilter(request, response);
		}
	}
	public void destroy() {}
	

}
