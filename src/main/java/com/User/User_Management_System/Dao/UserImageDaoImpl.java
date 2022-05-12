package com.User.User_Management_System.Dao;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.User.User_Management_System.Bean.UserImage;

public class UserImageDaoImpl extends GenericDaoImpl<UserImage>  implements UserImageDao{
	static final Logger LOG = LogManager.getLogger(UserImageDaoImpl.class.getName());
	@Autowired
	private HibernateTemplate hibernateTemplate;
	String query = "from UserImage where imgid=?0";
	@Autowired
	UserImage userimg;
	@Override
	public UserImage getImage(int imgid) {
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<UserImage> list = (List<UserImage>) hibernateTemplate.find(query, imgid);
        if(list!=null && (list.size() > 0))
        {
        	userimg=list.get(0);
        }
		return userimg;
	}
	}
