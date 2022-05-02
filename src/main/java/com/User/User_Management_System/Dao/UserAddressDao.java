package com.User.User_Management_System.Dao;

import java.util.List;

import com.User.User_Management_System.Bean.UserAddress;

public interface UserAddressDao {
	public void addUserAddress(UserAddress add);
	public List<UserAddress> getUserAddress(int userid);
	public void updateUserAddress(UserAddress add);
	public void deleteAddress(int addid);
}

