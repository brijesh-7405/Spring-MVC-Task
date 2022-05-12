package com.User.User_Management_System.Dao;

import java.util.List;
import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;

public interface UserDao extends GenericDao<User>{
	public boolean userExist(String mail);
	public List<User> getUserList();
	public User validUser(String email);
	public List<UserAddress> getUserAddress(int userid);
}
