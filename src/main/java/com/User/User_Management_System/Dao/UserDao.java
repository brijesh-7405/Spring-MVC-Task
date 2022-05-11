package com.User.User_Management_System.Dao;

import java.util.List;
import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;

public interface UserDao extends GenericDao<User>{
	public boolean userExist(String mail);
	//public int registerUser(User user);
//	public int getUserId(String mail);
//	public String getRole(String mail);
	public List<User> getUserList();
	//public void deleteUser(User user);
	public void changePwd(User user);
//	public void update(User user);
	public User validUser(String email);
//	public User getUserDetails(int userid); 
	public List<UserAddress> getUserAddress(int userid);
	//public void deleteAddress(UserAddress address);
}
