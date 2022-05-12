package com.User.User_Management_System.Service;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;

import java.util.*;
public interface UserService{
	public boolean userExist(String mail);
	public void registerUser(User user);
//	public int getUser(String mail);
	public User checkUser(String email);
//	public String getRole(String mail);
	public List<User> getUsers();
	public void deleteUser(int userid);
	public void changePwd(User user);
	public void updateUserProfile(User user);
	//public User getUserDetails(int userid);
	public User getUserDetails(int userID);
	public List<UserAddress> getUserAddress(int userid);
	public void deleteAddress(UserAddress address);
	//public void deleteImage(int imgid);
	public void deleteImage(int imageid);
}
