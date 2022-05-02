package com.User.User_Management_System.Service;

import java.util.List;

import com.User.User_Management_System.Bean.UserAddress;

public interface UserAddressService {
	
	public void addUserAddress(UserAddress useradd);
	public void updateUserAddress(UserAddress useradd);
	public List<UserAddress> getUserAddress(int userid);
	public void deleteAddress(int addid);
}
