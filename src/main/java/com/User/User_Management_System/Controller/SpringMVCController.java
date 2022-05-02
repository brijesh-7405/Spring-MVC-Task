package com.User.User_Management_System.Controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.Service.UserAddressService;
import com.User.User_Management_System.Service.UserAddressServiceImpl;
import com.User.User_Management_System.Service.UserImageService;
import com.User.User_Management_System.Service.UserImageServiceImpl;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;


@SuppressWarnings("serial")
@javax.servlet.annotation.MultipartConfig
@Controller
public class SpringMVCController extends HttpServlet{
	static final Logger LOG = LogManager.getLogger(SpringMVCController.class.getName());
	
	UserService userservice=  new UserServiceImpl();
	UserAddressService userAddressService = new UserAddressServiceImpl();
	UserImageService userImageService = new UserImageServiceImpl();
	@RequestMapping({"/","/index"})
	public String index()
	{
		return "index";
	}
	
	@RequestMapping("/forgotpwd")
	public String forgotpwd()
	{
		return "forgotpwd";
	}
	@RequestMapping("/registration2")
	public String register()
	{
		return "registration2";
	}
	@RequestMapping("/resetpwd")
	public String resetpwd()
	{
		return "resetpwd";
	}
	@RequestMapping("/userDashBoard")
	public String userDashBoard()
	{
		return "userDashBoard";
	}
	@PostMapping("/LoginServlet")
	public String login(HttpServletRequest request,HttpSession session,Model model,@RequestParam String email)
	{
		String pwd = (String) request.getAttribute("password");
	
		User user = userservice.checkUser(email);     //check the user is present in database or not
		if(user!=null)
			{
				if(pwd.equals(user.getPassword()))
				{
					String role = userservice.getRole(email);
					session.setAttribute("USER",user);
					if(role.equals("user"))
					{
						 LOG.debug("User-logged-in"); 
						return "redirect:userDashBoard";
			           
					}
					else
					{
						LOG.debug("Admin-logged-in");
						return "redirect:AdminWork";
						  
					}
				}
				else
				{
					model.addAttribute("message","*Invalid Password");
					return "index";
				}
			}
			else
			{
				LOG.error("Login fails"); 
				model.addAttribute("message","*Unauthorized User");
				return "index";
			}
	}
	@RequestMapping("/LogOut")
	public String logout(HttpServletRequest request,HttpSession session)
	{
        session=request.getSession(false); 
        if(session!=null)
        {
	         session.invalidate(); 								//Session close or Session Invalidate after logout user
        }   
        LOG.debug("Successfully logged out");
        return "index";
	}
	
	@RequestMapping("/AdminWork")
	public String adminPanel(Model model)
	{
		List<User> users;
		users = userservice.getUsers();                     //Calling a method who returns the all users list 
	 	model.addAttribute("UsersList",users);			//Storing users list into request Attribute
	 	LOG.info("userlist updated");
	 	return "adminDashBoard";
	}
	
	@RequestMapping("/UserRegistration")
	public String register(
							@RequestParam("firstname") String fname,
							@RequestParam("lastname") String lname,
							@RequestParam String phone,
							@RequestParam("q1") String ans1,
							@RequestParam("q2") String ans2,
							@RequestParam String email,
							@RequestParam String []lang,
							@RequestParam String birthdate,
							@RequestParam("Gender") String gender,
							@RequestParam String []address1,
							@RequestParam String []address2,
							@RequestParam String []city,
							@RequestParam String []state,
							@RequestParam String []country,
							@RequestParam String []pincode,
							HttpServletRequest request)
	{
				String pwd = (String) request.getAttribute("password");
//				String fname=request.getParameter("firstname");  
//				String lname=request.getParameter("lastname"); 
//				String phone=request.getParameter("phone");
//				String birthdate=request.getParameter("birthdate");
//				String ans1=request.getParameter("q1");
//				String ans2=request.getParameter("q2");
//				String gender=request.getParameter("Gender");
//				String address1[]=request.getParameterValues("address1");
//				String address2[]=request.getParameterValues("address2");
//				String[] pincode=request.getParameterValues("pincode");
//				String[] city=request.getParameterValues("city");
//				String[] state=request.getParameterValues("state");
//				String[] country=request.getParameterValues("country");
//				String lang[]=request.getParameterValues("lang");
				
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
				try {
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
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
		        LOG.info("Registration Successfully");
		        HttpSession session=request.getSession(false);
		        if(session.getAttribute("USER") == null)           //Check if session has no attribute the redirect it to login page
		        {
		        	return "index";
		        }
		        else
		        {
		        	return "redirect:AdminWork";      //else if admin add new user from its login side then redirect it to admin panel
		        }
				
	}
	@RequestMapping("/ForgotPwd")
	public String afterforgotpwd(Model model,@RequestParam String email
									,@RequestParam String birthdate
									,@RequestParam("q1") String ans1
									,@RequestParam("q2") String ans2)
	{	
		User user = userservice.checkUser(email);			//check the user is present in database or not
		if(user!=null)															//check the user is present in database or not
		{
				if(birthdate.equals(user.getDateofbirth()))
				{
					if(ans1.equals(user.getAnswer1()) && ans2.equals(user.getAnswer2()))
					{
						LOG.debug("All details are correct");
						model.addAttribute("email",user.getEmail());
						return "resetpwd";
					}
					else
					{
						LOG.error("Security Answers are wrong");
						model.addAttribute("message","*Security Answers are wrong");
						return "forgotpwd";
					}
				}
				else
				{
					LOG.error("Invalid BirthDate");
					model.addAttribute("message","*Invalid BirthDate");
					return "forgotpwd";
				}
		}
		else
		{
			LOG.error("Invalid user");
			model.addAttribute("message","*Invalid User");
			return "forgotpwd";
		}
	}
	@RequestMapping("/ResetPassword")
	public String changepwd(HttpServletRequest request,@RequestParam String usermail)
	{
		String pwd = (String) request.getAttribute("password");
		UserService userservice=  new UserServiceImpl();
		LOG.info("Password is changed");
		userservice.changePwd(pwd,usermail);      //Calling method who change the password and reset to the database
		return "index";
	}
	@RequestMapping(value = "/CheckUserExistDone", method = RequestMethod.POST)
	@ResponseBody
	public String checkuserexist(@RequestParam("email") String email)
	{
		String message="";
		if(userservice.userExist(email))                          //Condition check at the registration time for new user that email exist or not
		{
			message = "*Email already exist";
		}
		return message;
	}
		@RequestMapping("/UserDetails")
		public String goingToEdit(HttpServletRequest request,HttpSession session,Model model)
		{
			session=request.getSession(false);
			User user = (User) session.getAttribute("USER");
			RequestDispatcher rf=request.getRequestDispatcher("registration.jsp");
			if(user.getRole().equals("user"))
			{
				model.addAttribute("user",user);					//if role is user then store the user from session in request attribute
				LOG.info("Updated User stored in Request");
			 	return "registration2";
			}
			else
			{
				String uid = request.getParameter("userid");
				int userid = Integer.parseInt(uid);
				User usr = userservice.getUserDetails(userid);      //else get the particular user from the user id which edit part is on Admin hand
				model.addAttribute("user", usr);
				LOG.info("Updated User stored in Request");
				return "registration2";
			}
		}
		//@RequestMapping(value = "/EditServlet", method = RequestMethod.GET)
		@RequestMapping("/EditServlet")
		public String edit(@RequestParam("firstname") String fname,
							@RequestParam("lastname") String lname,
							@RequestParam String phone,
							@RequestParam String []lang,
							@RequestParam String birthdate,
							@RequestParam("Gender") String gender,
							@RequestParam String []addressid,
							@RequestParam String []address1,
							@RequestParam String []address2,
							@RequestParam String []city,
							@RequestParam String []state,
							@RequestParam String []country,
							@RequestParam String []pincode,
							HttpServletRequest request)
		{
			String language="";
			StringBuffer buf = new StringBuffer();
			if(lang!=null)
			{
				for(int i=0;i< lang.length;i++){
					buf.append(" "+lang[i]);
				}
				language=buf.toString();
			}
			long number = Long.parseLong(phone); 
			
			User user = new User();
			user.setFirstname(fname);
			user.setLastname(lname);
			user.setPhone(number);
			user.setDateofbirth(birthdate);
			user.setGender(gender);
			user.setLanguage(language);
			String uid=request.getParameter("userid");
			int userid=Integer.parseInt(uid);
			userservice.updateUserProfile(user,userid); //user profile updated
			
			List<UserAddress> useraddresses =userAddressService.getUserAddress(userid);
			int index=0;
			int oldAddressid[] = new int[useraddresses.size()];
			int addressIdLength= addressid.length;
			int count=0;
			for(UserAddress ud:useraddresses)
			{	
				oldAddressid[index]=ud.getAddressid();
				if(count<addressIdLength && addressid[count].length()!=0)
				{
					int addrssid=Integer.parseInt(addressid[count]);
					if(oldAddressid[index]==addrssid)
					{
						count++;
					}
					else
					{
						LOG.debug("Address deleted");
						userAddressService.deleteAddress(oldAddressid[index]);     //user Address deleted
					}
				}
				else
				{
					LOG.debug("Address deleted");
					userAddressService.deleteAddress(oldAddressid[index]);   //user Address deleted
				}
				index++;
			}
			UserAddress useraddress;
			for(int i=0;i<addressid.length;i++)
			{	
				useraddress = new UserAddress();
				if(addressid[i].length()==0 || addressid[i].equals("0"))     
				{
					//add new address of the user in address table
					useraddress.setUserid(userid);
					useraddress.setAdd1(address1[i]);
					useraddress.setAdd2(address2[i]);
					useraddress.setPincode(pincode[i]);
					useraddress.setCity(city[i]);
					useraddress.setState(state[i]);
					useraddress.setCountry(country[i]);
					userAddressService.addUserAddress(useraddress);
					LOG.debug("New Address added");
				}
				else
				{
					//Update  the User Address of the particular Address Id
	 				int addrssid=Integer.parseInt(addressid[i]);
					useraddress.setUserid(userid);
					useraddress.setAddressid(addrssid);
					useraddress.setAdd1(address1[i]);
					useraddress.setAdd2(address2[i]);
					useraddress.setPincode(pincode[i]);
					useraddress.setCity(city[i]);
					useraddress.setState(state[i]);
					useraddress.setCountry(country[i]);
					userAddressService.updateUserAddress(useraddress);
					LOG.debug("Address Updated");
				}
			}
			//Image Addition to the user
			try 
			{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			ArrayList<Part> fileParts = (ArrayList) request.getParts().stream().filter(new Predicate<Part>() {
				public boolean test(Part part) {
				return "image[]".equals(part.getName()) && part.getSize() > 0;
			}
			}).collect(Collectors.toList()); 
		    InputStream inputStream;
			UserImage userimg;
	        for (Part filePart : fileParts) {
	            if (filePart != null && filePart.getSize() != 0) {
	            	 userimg = new UserImage();
	                inputStream = filePart.getInputStream();
	                userimg.setUserid(userid);
	                userimg.setImage(inputStream);
	                userImageService.addUserImg(userimg);
	                LOG.debug("New Images added");
	            }
	        }
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
	        
	        return "redirect:UserData";
		}
		
		@RequestMapping("/UserData")
		public String userData(HttpServletRequest request) 
		{
			LOG.debug("Enter in userdata servlet");
			HttpSession session=request.getSession(false);             //Getting session
			User user = (User) session.getAttribute("USER"); 			 //Getting session attribute
			if(user.getRole().equals("admin"))							//Check the role of the user if admin then redirect to his Servlet
			{
				LOG.info("Admin is in Session");
				return "redirect:AdminWork";
			}
			else														//If the  role of the user is user then update the user details in the database and then stored that updated user in session  
			{
				user = userservice.checkUser(user.getEmail());
				session.setAttribute("USER", user);
				LOG.info("Updated User - stored in session");
				return "redirect:userDashBoard";
			}
		}
		@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
		@ResponseBody
		public String deleteUser(@RequestParam String userid)
		{
			LOG.debug("Enter in Delete User servlet");
			int uid = Integer.parseInt(userid);
			userservice.deleteUser(uid);               //Calling a method to delete the user from the database
			LOG.debug("User deleted");
			return "redirect:AdminWork";
		}
	
}
