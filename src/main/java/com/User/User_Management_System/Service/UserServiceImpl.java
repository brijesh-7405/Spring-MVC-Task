package com.User.User_Management_System.Service;

import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.User.User_Management_System.Bean.*;
import com.User.User_Management_System.Dao.*;
import com.User.User_Management_System.UtilityClass.EncryptPwd;
@Transactional
public class UserServiceImpl implements UserService{
	static final Logger LOG = LogManager.getLogger(UserServiceImpl.class.getName());
	@Autowired
	@Qualifier("userdao")
	private UserDao userdao;
	@Autowired
	@Qualifier("useraddressdao")
	private UserAddressDao useraddressdao;
	@Autowired
	@Qualifier("userimagedao")
	private UserImageDao userimagedao;
	@Autowired
	private EncryptPwd encrypt;
		public boolean userExist(String mail)
		{
			LOG.info("User service,userExist methods call");
			boolean status = userdao.userExist(mail);
			return status;
		}
		public void registerUser(User user)
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
			List<UserImage>  img = user.getPic();
			for(UserImage i:img) 
			{
				i.setUser(user);
			}
			
			user.setPic(img);
			userdao.create(user);
		}
		public User checkUser(String email)
		{
			LOG.info("User service,checkUser methods call");
			User user = userdao.validUser(email);
			List<UserImage> userimg = user.getPic();
			for(UserImage userimage : userimg)
			{  	  
		       	 String base64Image = Base64.getEncoder().encodeToString(userimage.getImgbytes());
		       	 userimage.setBase64Image(base64Image);
		       	 userimage.setImgid(userimage.getImgid());
			}
			user.setPic(userimg);
			return user;
		}
		public void changePwd(User user) 
		{
			LOG.info("User service,changePwd methods call");
			userdao.update(user);
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
			User user = userdao.find(userid);
			userdao.delete(user);
		}
		public void updateUserProfile(User user)
		{
			LOG.info("User service,updateUserProfile methods call");
			List<UserAddress>  add = user.getAddress();
			for(UserAddress a:add) 
			{
				a.setUser(user);
			}
			user.setAddress(add);
			
			List<UserImage>  img = user.getPic();
			for(UserImage i:img) 
			{
				i.setUser(user);
			}
			
			user.setPic(img);
			userdao.update(user);
		}
		public User getUserDetails(int userID)
		{
			LOG.info("User service,getUserDetails methods call");
			User user = userdao.find(userID);
			List<UserImage> userimg = user.getPic();
			for(UserImage userimage : userimg)
			{  	  
		       	 String base64Image = Base64.getEncoder().encodeToString(userimage.getImgbytes());
		       	 userimage.setBase64Image(base64Image);
		       	 userimage.setImgid(userimage.getImgid());
			}
			user.setPic(userimg);
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
			useraddressdao.delete(address);
		}
		public void deleteImage(int imgid)
		{
			LOG.info("User Image service,deleteImage methods call");
			UserImage userimg = userimagedao.find(imgid);
			userimagedao.delete(userimg);
		}
}
