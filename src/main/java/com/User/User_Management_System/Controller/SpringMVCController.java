package com.User.User_Management_System.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.UtilityClass.CheckValidation;
import com.User.User_Management_System.UtilityClass.EncryptPwd;

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
	@RequestMapping({"/","/index"})
	public String index()
	{
		return "index";
	}
	@RequestMapping("/registration")
	public String register()
	{
		return "registration";
	}
	@RequestMapping("/forgotpwd")
	public String forgotpwd()
	{
		return "forgotpwd";
	}
	@PostMapping(path="/userRegistration", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String registerUser(@Valid @ModelAttribute User  user,BindingResult br,Model model,HttpSession session,@RequestParam("image[]") CommonsMultipartFile[] files,HttpServletRequest request,@RequestParam("repass") String repass) throws IOException, ServletException
	{
		String msg=val.validData(user,repass);
		List<String> errors = new ArrayList<String>();
		if(br.hasErrors())  
        { 
			List<FieldError> error =br.getFieldErrors();
			for(FieldError err: error)
			{
				errors.add(err.getDefaultMessage());

			}			
			model.addAttribute("message",errors);
			model.addAttribute("faildata",user);
			return "registration";
        }
		else if(!msg.equals("valid"))
		{
			errors.add(msg);
			model.addAttribute("message",errors);
			model.addAttribute("faildata",user);
			return "registration";
		}
		else
		{
			List<UserImage> userimg = new ArrayList<UserImage>();
			UserImage img;
			if (files != null && files.length > 0) 
			{
	            for (CommonsMultipartFile aFile : files)
	            {
	            	img= new UserImage();
	                img.setImgbytes(aFile.getBytes());
	                userimg.add(img);              
	            }
	        }
			user.setPic(userimg);
			userservice.registerUser(user);
			session=request.getSession(false);
	        if(session.getAttribute("USER") != null)           
	        {
	        	return "redirect:adminWork";      //if admin add new user from its login side then redirect it to admin panel
	        	
	        }
	        else
	        {
	        	return "redirect:index";   //Check if session has no attribute the redirect it to login page
	        }
		}
		
	}
	@RequestMapping(value = "/checkUserExistDone", method = RequestMethod.POST)
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
	
	@PostMapping("/loginServlet")
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
						return "redirect:adminWork";
						  
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
	@RequestMapping("/adminWork")
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
	@RequestMapping("/logOut")
	public String logout(HttpServletRequest request,HttpSession session)
	{
        session=request.getSession(false); 
        if(session!=null)
        {
	         session.invalidate(); 								//Session close or Session Invalidate after logout user
        }   
        LOG.debug("Successfully logged out");
        return "redirect:index";
	}
	@RequestMapping("/forgotPwd")
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
	@RequestMapping("/resetpwd")
	public String resetpwd()
	{
		return "resetpwd";
	}
	@RequestMapping("/resetPassword")
	public String changepwd(HttpServletRequest request,@RequestParam("usermail") String usermail,@RequestParam String password)
	{
		String pwd = encrypt.encryption(password);
		User user = userservice.checkUser(usermail);
		user.setPassword(pwd);
		LOG.info("Password is changed");
		userservice.changePwd(user);      //Calling method who change the password and reset to the database
		return "redirect:index";
	}
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@RequestParam String userid)
	{
		LOG.debug("Enter in Delete User servlet");
		int uid = Integer.parseInt(userid);
		userservice.deleteUser(uid);               //Calling a method to delete the user from the database
		LOG.debug("User deleted");
		return "redirect:adminWork";
	}
	@RequestMapping("/userDetails")
	public String goingToEdit(HttpServletRequest request,HttpSession session,Model model)
	{
		session=request.getSession(false);
		User user = (User) session.getAttribute("USER");
		if(user.getRole().equals("user"))
		{
			model.addAttribute("user",user);					//if role is user then store the user from session in request attribute
			LOG.info("Updated User stored in Request");
		 	return "registration";
		}
		else
		{
			String uid = request.getParameter("userid");
			int userid = Integer.parseInt(uid);
			User usr = userservice.getUserDetails(userid);      //else get the particular user from the user id which edit part is on Admin hand
			model.addAttribute("user", usr);
			LOG.info("Updated User stored in Request");
			return "registration";
		}
	}
	@PostMapping(path="/editServlet", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String edit(@Valid @ModelAttribute User  user,BindingResult br,Model model,HttpSession session,@RequestParam("image[]") CommonsMultipartFile[] files,HttpServletRequest request,@RequestParam("addressid") String [] addressid)
	{
		if(br.hasErrors())  
        { 
			List<FieldError> error =br.getFieldErrors();
			List<String> errors = new ArrayList<String>();
			for(FieldError err: error)
			{
				errors.add(err.getDefaultMessage());
			}
			model.addAttribute("message",errors);
			User usr = userservice.getUserDetails(user.getUserID());
			user.setPic(usr.getPic());
			model.addAttribute("user",user);
			return "registration";
        }
		else
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
				
				List<UserImage> userimg = new ArrayList<UserImage>();
				UserImage img;
				if (files != null && files.length > 0) 
				{
		            for (CommonsMultipartFile aFile : files)
		            {
		                if(aFile.getSize()>0)
		                {
		                	img = new UserImage();
			                img.setImgbytes(aFile.getBytes());
			                userimg.add(img); 
		                }
		            }
		        }
				user.setPic(userimg);
				userservice.updateUserProfile(user); //user profile updated
		        
		        return "redirect:userData";
		}
	}
	@RequestMapping("/userData")
	public String userData(HttpServletRequest request,HttpSession session) 
	{
		LOG.debug("Enter in userdata servlet");
		session=request.getSession(false);             //Getting session
		User user = (User) session.getAttribute("USER"); 			 //Getting session attribute
		if(user.getRole().equals("admin"))							//Check the role of the user if admin then redirect to his Servlet
		{
			LOG.info("Admin is in Session");
			return "redirect:adminWork";
		}
		else														//If the  role of the user is user then update the user details in the database and then stored that updated user in session  
		{
			user = userservice.checkUser(user.getEmail());
			session.setAttribute("USER", user);
			LOG.info("Updated User - stored in session");
			return "redirect:userDashBoard";
		}
	}
	@PostMapping("/removeImage")
	@ResponseBody
	public String deleteUserImage(@RequestParam String imgId)
	{ 
		String message="";
		int imageid = Integer.parseInt(imgId);
		userservice.deleteImage(imageid);         //Delete image from the database 
		LOG.debug("image-deleted");
		message="image deleted succesfully";
		return message;
	}
}
