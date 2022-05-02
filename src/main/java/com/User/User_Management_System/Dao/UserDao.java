package com.User.User_Management_System.Dao;

import java.util.List;
import com.User.User_Management_System.Bean.User;

public interface UserDao {
	public boolean userExist(String mail);
	public void registerUser(User user);
	public int getUserId(String mail);
	public String getRole(String mail);
	public List<User> getUserList();
	public void deleteUser(int userid);
	public void changePwd(String pwd, String usermail); 
	public void updateUserProfile(User user,int userid);
	public User validUser(String email);
	public User getUserDetails(int userid); 
}
