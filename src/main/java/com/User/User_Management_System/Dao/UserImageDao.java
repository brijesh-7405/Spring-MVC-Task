package com.User.User_Management_System.Dao;

import java.util.List;

import com.User.User_Management_System.Bean.UserImage;

public interface UserImageDao {
	
	public void addUserImage(UserImage img);
	public List<UserImage> getUserImg(int userid);
	public void deleteImage(int imgid);
}
