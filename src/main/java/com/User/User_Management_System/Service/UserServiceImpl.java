package com.User.User_Management_System.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.*;
import com.User.User_Management_System.Dao.*;

public class UserServiceImpl implements UserService{
	static final Logger LOG = LogManager.getLogger(UserServiceImpl.class.getName());
	private transient UserDao userdao = new UserDaoImpl();
		public boolean userExist(String mail)
		{
			LOG.info("User service,userExist methods call");
			boolean status = userdao.userExist(mail);
			return status;
		}
		public void registerUser(User user)
		{
			LOG.info("User service,registerUser methods call");
			userdao.registerUser(user);
		}
		public int getUser(String mail)
		{
			LOG.info("User service,getUser methods call");
			int id = userdao.getUserId(mail);
			return id;
		}
		public User checkUser(String email)
		{
			LOG.info("User service,checkUser methods call");
			User user = userdao.validUser(email);
			return user;
		}
		public String getRole(String mail)
		{
			LOG.info("User service,getRole methods call");
			String role = userdao.getRole(mail);
			return role;
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
			userdao.deleteUser(userid);
		}
		public void changePwd(String pwd,String usermail) {
			LOG.info("User service,changePwd methods call");
			userdao.changePwd(pwd,usermail);
		}
		public void updateUserProfile(User user,int userid)
		{
			LOG.info("User service,updateUserProfile methods call");
			userdao.updateUserProfile(user,userid);
		}
		public User getUserDetails(int userid)
		{
			LOG.info("User service,getUserDetails methods call");
			User user = userdao.getUserDetails(userid);
			return user;
		}
}
