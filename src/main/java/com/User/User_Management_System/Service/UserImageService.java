package com.User.User_Management_System.Service;

import java.util.List;

import com.User.User_Management_System.Bean.UserImage;

public interface UserImageService {
	
	public void addUserImg(UserImage userimg);
	public void deleteImage(int imgid);
	public List<UserImage> getUserImg(int userid);
}
