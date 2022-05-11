package com.User.User_Management_System.Service;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.User.User_Management_System.Bean.*;
import com.User.User_Management_System.Dao.*;
import com.User.User_Management_System.UtilityClass.EncryptPwd;

public class UserServiceImpl implements UserService{
	static final Logger LOG = LogManager.getLogger(UserServiceImpl.class.getName());
	@Autowired
	@Qualifier("userdao")
	private UserDao userdao;
	@Autowired
	private EncryptPwd encrypt;
	@Autowired
	User user;
		public boolean userExist(String mail)
		{
			LOG.info("User service,userExist methods call");
			boolean status = userdao.userExist(mail);
			return status;
		}
		public int registerUser(User user)
		{
			LOG.info("User service,registerUser methods call");
			String pwd = encrypt.encryption(user.getPassword());
			user.setPassword(pwd);
			user.setRole("user");
			List<UserAddress>  add = user.getAddress();
			for(UserAddress a:add) 
			{
				a.setUser(user);
			}
			user.setAddress(add);
//			List<UserImage>  img = user.getImage();
//			for(UserImage image:img) 
//			{
//				image.setUser(user);
//			}
//			user.setImage(img);
			int id = userdao.registerUser(user);
			return id;
		}
		public User checkUser(String email)
		{
			LOG.info("User service,checkUser methods call");
			User user = userdao.validUser(email);
			return user;
		}
		public void changePwd(User user) 
		{
			LOG.info("User service,changePwd methods call");
			userdao.changePwd(user);
		}
		public List<User> getUsers()
		{
			LOG.info("User service,getUsers methods call");
			List<User> userlist;
			userlist = userdao.getUserList();
			return userlist;
		}
		public void deleteUser(int userid)
		{
			LOG.info("User service,deleteUser methods call");
			User user = userdao.getUserDetails(userid);
			userdao.deleteUser(user);
		}
		public void updateUserProfile(User user)
		{
			LOG.info("User service,updateUserProfile methods call");
			List<UserAddress>  add = user.getAddress();
			for(UserAddress a:add) 
			{
				a.setUser(user);
			}
			System.out.println("sevcdscdd"+user.getUserID());
			System.out.println("Sercivc:"+add);
			user.setAddress(add);
			userdao.update(user);
		}
		public User getUserDetails(int userID)
		{
			LOG.info("User service,getUserDetails methods call");
			User user = userdao.getUserDetails(userID);
			return user;
		}
		public List<UserAddress> getUserAddress(int userid)
		{
			LOG.info("User Address service,getUserAddress methods call");
			List<UserAddress> useraddlist = userdao.getUserAddress(userid);
			return useraddlist;
		}
		public void deleteAddress(UserAddress address)
		{
			LOG.info("User Address service,deleteAddress methods call");
			userdao.deleteAddress(address);
		}
		
//		public String getRole(String mail)
//		{
//			LOG.info("User service,getRole methods call");
//			String role = userdao.getRole(mail);
//			return role;
//		}
//		public int getUser(String mail)
//		{
//			LOG.info("User service,getUser methods call");
//			int id = userdao.getUserId(mail);
//			return id;
//		}




//		public void updateUserProfile(User user,int userid)
//		{
//			LOG.info("User service,updateUserProfile methods call");
//			userdao.updateUserProfile(user,userid);
//		}
//		public User getUserDetails(int userid)
//		{
//			LOG.info("User service,getUserDetails methods call");
//			User user = userdao.getUserDetails(userid);
//			return user;
//		}
}
