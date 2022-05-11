package com.User.User_Management_System.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Media;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.Service.UserImageService;
import com.User.User_Management_System.Service.UserImageServiceImpl;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;
import com.User.User_Management_System.UtilityClass.CheckValidation;
import com.User.User_Management_System.UtilityClass.EncryptPwd;
import com.User.User_Management_System.UtilityClass.Validation;

@Controller
public class SpringMVCController{
	static final Logger LOG = LogManager.getLogger(SpringMVCController.class.getName());
	@Autowired
	@Qualifier("userservice")
	private UserService userservice;
	@Autowired
	private EncryptPwd encrypt;
	@Autowired
	private CheckValidation val;
	@Autowired
	UserAddress useraddress;
	
	@RequestMapping({"/","/index"})
	public String index()
	{
		return "index";
	}
	@RequestMapping("/registration2")
	public String register()
	{
		return "registration2";
	}
	@RequestMapping("/forgotpwd")
	public String forgotpwd()
	{
		return "forgotpwd";
	}
	@PostMapping(path="/UserRegistration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String registerUser(@Valid @ModelAttribute User  user,BindingResult br,Model model,HttpSession session,@RequestParam("image[]") CommonsMultipartFile[] files,HttpServletRequest request,@RequestParam("repass") String repass) throws IOException, ServletException
	{
		String msg="";
		if(br.hasErrors())  
        { 
			List<FieldError> error =br.getFieldErrors();
			String errors ="";
			for(FieldError err: error)
			{
				errors += err.getDefaultMessage() + "<br>";
			}
			model.addAttribute("message",errors);
			model.addAttribute("faildata",user);
			return "registration2";
        }
		else if(userservice.userExist(user.getEmail()))
		{
			LOG.info("*Email already exist");
			msg= "*Email already exist";
			model.addAttribute("message",msg);
			model.addAttribute("faildata",user);
			return "registration2";
		}
		else if(String.valueOf(user.getPhone()).length()<10)
		{
			msg= "*Number not less than 10 Digits";
			model.addAttribute("message",msg);
			model.addAttribute("faildata",user);
			return "registration2";
		}
		else if(val.validatepwd(user.getPassword()))
		{
			msg="*Please Choose Strong Password.";
			model.addAttribute("message",msg);
			model.addAttribute("faildata",user);
			return "registration2";
		}
		else if(!user.getPassword().equals(repass))
		{
			msg= "*Confirm password Should be same as Password.";
			model.addAttribute("message",msg);
			model.addAttribute("faildata",user);
			return "registration2";
		}
		else
		{
//		List<UserImage> userimg = new ArrayList<UserImage>();
//		InputStream inputStream = null;
//		UserImage img=null;
//        for (Part filePart : fileParts) {
//            if (filePart != null && filePart.getSize() != 0) {
//                inputStream = filePart.getInputStream();
//                byte[] imgbytes = IOUtils.toByteArray(inputStream);
//                img.setImgbytes(imgbytes);
//                userimg.add(img);
//            }
//            LOG.debug("User Images Added in database");
//        }
//			List<UserImage> userimg = new ArrayList<UserImage>();
//		InputStream inputStream = null;
//        for (MultipartFile filePart : files) {
//            if (filePart != null && filePart.getSize() != 0) {
//            	UserImage img = null;
//                inputStream = filePart.getInputStream();
//                byte[] imgbytes = IOUtils.toByteArray(inputStream);
//                img.setImgbytes(imgbytes);
//                userimg.add(img);
////                ((UserImage) userimg).setImage(inputStream);
//            }
//        }
//        user.setImage(userimg);
//		System.out.println(user.getImage());
//			List<UserImage> userimg = new ArrayList<UserImage>();
//			UserImage img=null;
//			if (files != null && files.length > 0) 
//			{
//	            for (CommonsMultipartFile aFile : files)
//	            {
//	                  
//	                System.out.println("Saving file: " + aFile.getOriginalFilename());
//	                img.setImgbytes(aFile.getBytes());
//	                userimg.add(img);              
//	            }
//	        }
//			user.setImage(userimg);
			userservice.registerUser(user);
			session=request.getSession(false);
	        if(session.getAttribute("USER") != null)           
	        {
	        	return "redirect:AdminWork";      //if admin add new user from its login side then redirect it to admin panel
	        	
	        }
	        else
	        {
	        	return "index";   //Check if session has no attribute the redirect it to login page
	        }
		}
		
	}
	@RequestMapping(value = "/CheckUserExistDone", method = RequestMethod.POST)
	@ResponseBody
	public String checkuserexist(@RequestParam("email") String email)
	{
		String message="";
		if(userservice.userExist(email))                         //Condition check at the registration time for new user that email exist or not
		{
			message = "*Email already exist";
		}
		return message;
	}
	
	@PostMapping("/LoginServlet")
	public String login(HttpServletRequest request,HttpSession session,Model model,@RequestParam String email,@RequestParam String password)
	{
		String pwd = encrypt.encryption(password);
		User user = userservice.checkUser(email);     //check the user is present in database or not
		if(user!=null)
			{
				if(pwd.equals(user.getPassword()))
				{
					String role = user.getRole();
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
	@RequestMapping("/AdminWork")
	public String adminPanel(Model model)
	{
		List<User> users;
		users = userservice.getUsers();                     //Calling a method who returns the all users list 
	 	model.addAttribute("UsersList",users);			//Storing users list into request Attribute
	 	LOG.info("userlist updated");
	 	return "adminDashBoard";
	}
	@RequestMapping("/userDashBoard")
	public String userDashBoard()
	{
		return "userDashBoard";
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
						System.out.println(user.getEmail());
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
	@RequestMapping("/resetpwd")
	public String resetpwd()
	{
		return "resetpwd";
	}
	@RequestMapping("/ResetPassword")
	public String changepwd(HttpServletRequest request,@RequestParam("usermail") String usermail,@RequestParam String password)
	{
		String pwd = encrypt.encryption(password);
		User user = userservice.checkUser(usermail);
		user.setPassword(pwd);
		LOG.info("Password is changed");
		userservice.changePwd(user);      //Calling method who change the password and reset to the database
		return "index";
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
	@RequestMapping("/UserDetails")
	public String goingToEdit(HttpServletRequest request,HttpSession session,Model model)
	{
		session=request.getSession(false);
		User user = (User) session.getAttribute("USER");
		System.out.println(user.getRole());
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
	@PostMapping(path="/EditServlet", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String edit(@ModelAttribute User  user,BindingResult br,Model model,HttpSession session,@RequestParam("image[]") MultipartFile[] files,HttpServletRequest request,@RequestParam("addressid") String [] addressid)
	{
//		if(br.hasErrors())  
//        { 
//			System.out.println("nmxnmxcnxm");
//			List<FieldError> error =br.getFieldErrors();
//			String errors ="";
//			for(FieldError err: error)
//			{
//				errors += err.getDefaultMessage() + "<br>";
//				System.out.println("error: "+err);
//			}
//			model.addAttribute("message",errors);
//			model.addAttribute("user",user);
//			return "registration2";
//        }
//		else
		{
		User oldUser = userservice.getUserDetails(user.getUserID());
		user.setAnswer1(oldUser.getAnswer1());
		user.setAnswer2(oldUser.getAnswer2());
		user.setEmail(oldUser.getEmail());
		user.setPassword(oldUser.getPassword());
		user.setRole(oldUser.getRole());
		
		List<UserAddress> useraddresses =userservice.getUserAddress(user.getUserID());
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
					userservice.deleteAddress(ud);     //user Address deleted
				}
			}
			else
			{
				LOG.debug("Address deleted");
				userservice.deleteAddress(ud);   //user Address deleted
			}
			index++;
		}
		System.out.println("User address:: "+useraddresses);
		List<UserAddress> addresslist = user.getAddress() ;
		for(int i=0;i<addressid.length;i++)
		{	
			if(addressid[i].length()==0 || addressid[i].equals("0"))     
			{
				//add new address of the user in address table
				addresslist.get(i).setAddressid(0);
				LOG.debug("New Address added");
			}
			else
			{
				//Update  the User Address of the particular Address Id
 				int addrssid=Integer.parseInt(addressid[i]);
 				addresslist.get(i).setAddressid(addrssid);
				LOG.debug("Address Updated");
			}
		}
		user.setAddress(addresslist);
		System.out.println("Controller: "+user.getAddress());
		userservice.updateUserProfile(user); //user profile updated
//		//Image Addition to the user
//		try 
//		{
//	    InputStream inputStream;
//		UserImage userimg;
//        for (MultipartFile filePart : files) {
//            if (filePart != null && filePart.getSize() != 0) {
//            	 userimg = new UserImage();
//                inputStream = filePart.getInputStream();
//                userimg.setUserid(userid);
//                userimg.setImage(inputStream);
//                userImageService.addUserImg(userimg);
//                LOG.debug("New Images added");
//            }
//        }
//		}
//		catch(Exception e)
//		{
//			System.out.println(e);
//		}
        
        return "redirect:UserData";
		}
	}
	@RequestMapping("/UserData")
	public String userData(HttpServletRequest request,HttpSession session) 
	{
		LOG.debug("Enter in userdata servlet");
		session=request.getSession(false);             //Getting session
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
}

//	@RequestMapping("/registration2")
//	public String register()
//	{
//		return "registration2";
//	}


//	@PostMapping("/LoginServlet")
//	public String login(HttpServletRequest request,HttpSession session,Model model,@RequestParam String email,@RequestParam String password)
//	{
//		String pwd = ency.encryption(password);
//	
//		User user = userservice.checkUser(email);     //check the user is present in database or not
//		if(user!=null)
//			{
//				if(pwd.equals(user.getPassword()))
//				{
//					String role = userservice.getRole(email);
//					session.setAttribute("USER",user);
//					if(role.equals("user"))
//					{
//						 LOG.debug("User-logged-in"); 
//						return "redirect:userDashBoard";
//			           
//					}
//					else
//					{
//						LOG.debug("Admin-logged-in");
//						return "redirect:AdminWork";
//						  
//					}
//				}
//				else
//				{
//					model.addAttribute("message","*Invalid Password");
//					return "index";
//				}
//			}
//			else
//			{
//				LOG.error("Login fails"); 
//				model.addAttribute("message","*Unauthorized User");
//				return "index";
//			}
//	}

//	

//	@PostMapping(path="/UserRegistration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//	public String register(@RequestParam("firstname") String fname,
//							@RequestParam("lastname") String lname,
//							@RequestParam("password") String password,
//							@RequestParam String phone,
//							@RequestParam("q1") String ans1,
//							@RequestParam("q2") String ans2,
//							@RequestParam String email,
//							@RequestParam String []lang,
//							@RequestParam String birthdate,
//							@RequestParam("Gender") String gender,
//							@RequestParam String []address1,
//							@RequestParam String []address2,
//							@RequestParam String []city,
//							@RequestParam String []state,
//							@RequestParam String []country,
//							@RequestParam String []pincode,
//							@RequestParam("image[]") MultipartFile[] files,
//							HttpServletRequest request,
//							HttpSession session)
//	{
//		String pwd = ency.encryption(password);
//				
//				String language="";
//				StringBuffer buf = new StringBuffer();
//				for(int i=0;i< lang.length;i++){
//					buf.append(" "+lang[i]);
//				}
//				language=buf.toString();
//				long number = Long.parseLong(phone);
//				User user = new User();
//				user.setFirstname(fname);
//				user.setLastname(lname);
//				user.setEmail(email);
//				user.setPhone(number);
//				user.setPassword(pwd);
//				user.setDateofbirth(birthdate);
//				user.setAnswer1(ans1);
//				user.setAnswer2(ans2);
//				user.setGender(gender);
//				user.setLanguage(language);
//				
//				userservice.registerUser(user);    //Register user data in user database
//				LOG.debug("User data Added");
//				int userid=userservice.getUser(email);    //get user id which was just added into database
//				UserAddress useraddress;
//				for(int i=0;i<address1.length;i++)
//				{
//						useraddress= new UserAddress();
//						useraddress.setUserid(userid);
//						useraddress.setAdd1(address1[i]);
//						useraddress.setAdd2(address2[i]);
//						useraddress.setPincode(pincode[i]);
//						System.out.println("pin:"+pincode[i]);
//						useraddress.setCity(city[i]);
//						useraddress.setState(state[i]);
//						useraddress.setCountry(country[i]);
//						userAddressService.addUserAddress(useraddress);      //Add the user multiple address in address table
//				}
//				LOG.debug("User Addresses Added");
//				try {
//				
//			    InputStream inputStream = null;
//				UserImage userimg=null;
//		        for (MultipartFile filePart : files) {
//		            if (filePart != null && filePart.getSize() != 0) {
//		            	 userimg = new UserImage();
//		                inputStream = filePart.getInputStream();
//		                userimg.setUserid(userid);
//		                userimg.setImage(inputStream);
//		                userImageService.addUserImg(userimg);      //Add the user multiple Images in image table
//		            }
//		            LOG.debug("User Images Added in database");
//		        }
//		        LOG.debug("User Images Added");
//				}
//				catch(Exception e)
//				{
//					System.out.println(e);
//				}
//		        LOG.info("Registration Successfully");
//		        session=request.getSession(false);
//		        if(session.getAttribute("USER") != null)           
//		        {
//		        	return "redirect:AdminWork";      //if admin add new user from its login side then redirect it to admin panel
//		        	
//		        }
//		        else
//		        {
//		        	return "index";   //Check if session has no attribute the redirect it to login page
//		        }
//				
//	}



//		@RequestMapping("/UserDetails")
//		public String goingToEdit(HttpServletRequest request,HttpSession session,Model model)
//		{
//			session=request.getSession(false);
//			User user = (User) session.getAttribute("USER");
//			RequestDispatcher rf=request.getRequestDispatcher("registration.jsp");
//			if(user.getRole().equals("user"))
//			{
//				model.addAttribute("user",user);					//if role is user then store the user from session in request attribute
//				LOG.info("Updated User stored in Request");
//			 	return "registration2";
//			}
//			else
//			{
//				String uid = request.getParameter("userid");
//				int userid = Integer.parseInt(uid);
//				User usr = userservice.getUserDetails(userid);      //else get the particular user from the user id which edit part is on Admin hand
//				model.addAttribute("user", usr);
//				LOG.info("Updated User stored in Request");
//				return "registration2";
//			}
//		}
//		//@RequestMapping(value = "/EditServlet", method = RequestMethod.GET)
//		@PostMapping(path="/EditServlet", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//		public String edit(@RequestParam("firstname") String fname,
//							@RequestParam("lastname") String lname,
//							@RequestParam String phone,
//							@RequestParam String []lang,
//							@RequestParam String birthdate,
//							@RequestParam("Gender") String gender,
//							@RequestParam String []addressid,
//							@RequestParam String []address1,
//							@RequestParam String []address2,
//							@RequestParam String []city,
//							@RequestParam String []state,
//							@RequestParam String []country,
//							@RequestParam String []pincode,
//							@RequestParam("image[]") MultipartFile[] files,
//							HttpServletRequest request)
//		{
//			String language="";
//			StringBuffer buf = new StringBuffer();
//			if(lang!=null)
//			{
//				for(int i=0;i< lang.length;i++){
//					buf.append(" "+lang[i]);
//				}
//				language=buf.toString();
//			}
//			long number = Long.parseLong(phone); 
//			
//			User user = new User();
//			user.setFirstname(fname);
//			user.setLastname(lname);
//			user.setPhone(number);
//			user.setDateofbirth(birthdate);
//			user.setGender(gender);
//			user.setLanguage(language);
//			String uid=request.getParameter("userid");
//			int userid=Integer.parseInt(uid);
//			userservice.updateUserProfile(user,userid); //user profile updated
//			
//			List<UserAddress> useraddresses =userAddressService.getUserAddress(userid);
//			int index=0;
//			int oldAddressid[] = new int[useraddresses.size()];
//			int addressIdLength= addressid.length;
//			int count=0;
//			for(UserAddress ud:useraddresses)
//			{	
//				oldAddressid[index]=ud.getAddressid();
//				if(count<addressIdLength && addressid[count].length()!=0)
//				{
//					int addrssid=Integer.parseInt(addressid[count]);
//					if(oldAddressid[index]==addrssid)
//					{
//						count++;
//					}
//					else
//					{
//						LOG.debug("Address deleted");
//						userAddressService.deleteAddress(oldAddressid[index]);     //user Address deleted
//					}
//				}
//				else
//				{
//					LOG.debug("Address deleted");
//					userAddressService.deleteAddress(oldAddressid[index]);   //user Address deleted
//				}
//				index++;
//			}
//			UserAddress useraddress;
//			for(int i=0;i<addressid.length;i++)
//			{	
//				useraddress = new UserAddress();
//				if(addressid[i].length()==0 || addressid[i].equals("0"))     
//				{
//					//add new address of the user in address table
//					useraddress.setUserid(userid);
//					useraddress.setAdd1(address1[i]);
//					useraddress.setAdd2(address2[i]);
//					useraddress.setPincode(pincode[i]);
//					useraddress.setCity(city[i]);
//					useraddress.setState(state[i]);
//					useraddress.setCountry(country[i]);
//					userAddressService.addUserAddress(useraddress);
//					LOG.debug("New Address added");
//				}
//				else
//				{
//					//Update  the User Address of the particular Address Id
//	 				int addrssid=Integer.parseInt(addressid[i]);
//					useraddress.setUserid(userid);
//					useraddress.setAddressid(addrssid);
//					useraddress.setAdd1(address1[i]);
//					useraddress.setAdd2(address2[i]);
//					useraddress.setPincode(pincode[i]);
//					useraddress.setCity(city[i]);
//					useraddress.setState(state[i]);
//					useraddress.setCountry(country[i]);
//					userAddressService.updateUserAddress(useraddress);
//					LOG.debug("Address Updated");
//				}
//			}
//			//Image Addition to the user
//			try 
//			{
//		    InputStream inputStream;
//			UserImage userimg;
//	        for (MultipartFile filePart : files) {
//	            if (filePart != null && filePart.getSize() != 0) {
//	            	 userimg = new UserImage();
//	                inputStream = filePart.getInputStream();
//	                userimg.setUserid(userid);
//	                userimg.setImage(inputStream);
//	                userImageService.addUserImg(userimg);
//	                LOG.debug("New Images added");
//	            }
//	        }
//			}
//			catch(Exception e)
//			{
//				System.out.println(e);
//			}
//	        
//	        return "redirect:UserData";
//		}
//		
//		@RequestMapping("/UserData")
//		public String userData(HttpServletRequest request) 
//		{
//			LOG.debug("Enter in userdata servlet");
//			HttpSession session=request.getSession(false);             //Getting session
//			User user = (User) session.getAttribute("USER"); 			 //Getting session attribute
//			if(user.getRole().equals("admin"))							//Check the role of the user if admin then redirect to his Servlet
//			{
//				LOG.info("Admin is in Session");
//				return "redirect:AdminWork";
//			}
//			else														//If the  role of the user is user then update the user details in the database and then stored that updated user in session  
//			{
//				user = userservice.checkUser(user.getEmail());
//				session.setAttribute("USER", user);
//				LOG.info("Updated User - stored in session");
//				return "redirect:userDashBoard";
//			}
//		}
//		@RequestMapping(value = "/DeleteUser", method = RequestMethod.POST)
//		@ResponseBody
//		public String deleteUser(@RequestParam String userid)
//		{
//			LOG.debug("Enter in Delete User servlet");
//			int uid = Integer.parseInt(userid);
//			userservice.deleteUser(uid);               //Calling a method to delete the user from the database
//			LOG.debug("User deleted");
//			return "redirect:AdminWork";
//		}
//		
//		@PostMapping("/RemoveImage")
//		@ResponseBody
//		public String deleteUserImage(@RequestParam String imgId)
//		{ 
//			String message="";
//			int imageid = Integer.parseInt(imgId);
//			userImageService.deleteImage(imageid);         //Delete image from the database 
//			LOG.debug("image-deleted");
//			message="image deleted succesfully";
//			return message;
//		}
//		
//	
//}
