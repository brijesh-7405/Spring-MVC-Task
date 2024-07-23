package com.User.User_Management_System.Dao;

import java.util.*;
import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
	static final Logger LOG = LogManager.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	String query = "from User where email=?0";
	@Transactional
	public boolean userExist(String mail)
	{
		boolean status=false; 
			@SuppressWarnings({ "deprecation", "unchecked" })
			List<User> list = (List<User>) hibernateTemplate.find(query, mail);
            if(list!=null && (list.size() > 0))
            {
            	status=true;
            }
            else
            {
            	 status=false;
            }
           
		return status;
	}
	@Transactional
	public User validUser(String email)
	{
			User user = null;
			@SuppressWarnings({ "deprecation", "unchecked" })
			List<User> list = (List<User>) hibernateTemplate.find(query, email);
            if(list!=null && (list.size() > 0))
            {
            	user=list.get(0);
            }
            LOG.info("User data stored in bean");
        return user;
	}

	/* get All Users list from the database*/
	@Transactional
	public List<User> getUserList()
	{
		String getUserQuery = "from User where role='user'";
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<User> list = (List<User>) hibernateTemplate.find(getUserQuery);
		return list;
	}

	@Transactional
	public List<UserAddress> getUserAddress(int userid)
	{
		String getUserQuery = "from UserAddress where user_userID=?0";
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<UserAddress> list = (List<UserAddress>) hibernateTemplate.find(getUserQuery,userid);
		return list;
	}
	
}
