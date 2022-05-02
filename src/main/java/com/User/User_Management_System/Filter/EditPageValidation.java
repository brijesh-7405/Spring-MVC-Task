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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.Service.UserAddressService;
import com.User.User_Management_System.Service.UserAddressServiceImpl;
import com.User.User_Management_System.Service.UserImageService;
import com.User.User_Management_System.Service.UserImageServiceImpl;
import com.User.User_Management_System.UtilityClass.CheckValidation;
@MultipartConfig
public class EditPageValidation implements Filter {
	private transient  CheckValidation  val;
	private transient   UserImageService userImageService;
	private transient  UserAddressService userAddressService;
	 static final Logger LOG = LogManager.getLogger(EditPageValidation.class.getName());
		public void init(FilterConfig fConfig) throws ServletException {
			val = new CheckValidation();
			userImageService = new UserImageServiceImpl();
			userAddressService = new UserAddressServiceImpl();
		}
		
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.info("Edit page validation filter");
		response.setContentType("text/html");
		String fname=request.getParameter("firstname");  
		String lname=request.getParameter("lastname");
		String phone=request.getParameter("phone");
		String birthdate=request.getParameter("birthdate");
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
		if(val.validateNumber(phone))
		{
			request.setAttribute("phonenumber",phone);
		}
		else {
		 user.setPhone(Long.parseLong(phone));
		}
		user.setDateofbirth(birthdate);
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
		
		 String uid = request.getParameter("userid");
			int userid = Integer.parseInt(uid);
			user.setUserID(userid);
//		ArrayList<UserAddress> list = (ArrayList<UserAddress>) userAddressService.getUserAddress(userid);
//		user.setAddress(list);
			
			UserAddress useraddress;
			ArrayList<UserAddress> list = new ArrayList<UserAddress>();
			if(address1!=null)
			{
				String addressid[]=request.getParameterValues("addid");
				for(int i=0;i<address1.length;i++)
				{
						useraddress= new UserAddress();
						if(addressid[i].equals(""))
						{
							useraddress.setAddressid(0);
						}
						else
						{
							useraddress.setAddressid(Integer.parseInt(addressid[i]));
						}
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
       
		ArrayList<UserImage> imagelist =(ArrayList<UserImage>) userImageService.getUserImg(userid);
		user.setImage(imagelist);
		RequestDispatcher rd=request.getRequestDispatcher("registration.jsp"); 
		request.setAttribute("user",user);
		
		boolean validate=true;
		if(fname.equals("")||lname.equals("")||phone.equals("")||birthdate.equals("")||gender.equals(""))
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
		else
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
			LOG.info("Validation Successful");
			chain.doFilter(request, response);
		}
	}

	public void destroy() {}

}
