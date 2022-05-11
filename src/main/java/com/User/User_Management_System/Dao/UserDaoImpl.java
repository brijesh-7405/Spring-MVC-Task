package com.User.User_Management_System.Dao;

import java.sql.*;
import java.util.*;
import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
	static final Logger LOG = LogManager.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	String query = "from User where email=?0";
	
//	@Transactional
//	public int registerUser(User user)
//	{
//		int id = (int) hibernateTemplate.save(user);
//		return id;
//	}
	
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
	/*Change password an added to database*/
	@Transactional
	public void changePwd(User user) 
	{
		hibernateTemplate.merge(user);
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
	/*Get user all details*/
//	public User getUserDetails(int userid)
//	{
//		String getUserQuery = "from User where userID=?0";
//		User user = (User) hibernateTemplate.get(getUserQuery,userid);
//		//User user = null;
//		hibernateTemplate.get(, getUserQuery)
//		@SuppressWarnings({ "deprecation", "unchecked" })
//		List<User> list = (List<User>) hibernateTemplate.find(getUserQuery,userid);
//        if(list!=null && (list.size() > 0))
//        {
//        	user=list.get(0);
//        }
//        return user;
//	}
//	@Transactional
//	public void deleteUser(User user)
//	{
//		hibernateTemplate.delete(user);
//	}
	@Transactional
	public List<UserAddress> getUserAddress(int userid)
	{
		String getUserQuery = "from UserAddress where user_userID=?0";
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<UserAddress> list = (List<UserAddress>) hibernateTemplate.find(getUserQuery,userid);
		return list;
	}
//	@Transactional
//	public void deleteAddress(UserAddress address)
//	{
//		hibernateTemplate.delete(address);
//	}
//	@Transactional
//	public void update(User user)
//	{
//		hibernateTemplate.merge(user);
//	}
	/*Get user role*/
//	public String getRole(String mail)
//	{
//		String role="";
//		try 
//        {
//        	con=ConnectionSetup.getConnection();
//        	ps = con.prepareStatement("select Role from UserDetails where Email = ?;");
//            ps.setString(1,mail);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next())
//            {
//            	role=rs.getString(1);
//            	LOG.info("Check Role Successful");
//            }
//        }
//		catch(Exception e)
//        {
//			LOG.fatal(e);
//        }
//        finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//		return role;
//	}
//	/*Register user into the database*/
//	public void registerUser(User user)
//	{
//		try
//		{
//			con=ConnectionSetup.getConnection();
//			 ps=((java.sql.Connection) con).prepareStatement("insert into UserDetails(FirstName,LastName,Phone,DateOfBirth,Gender,LanguageKnown,Email,Password,Answer1,Answer2,Role) values(?,?,?,?,?,?,?,?,?,?,'user');");
//			 
//			 	ps.setString(1, user.getFirstname());
//	            ps.setString(2, user.getLastname());
//	            ps.setLong(3, user.getPhone());
//	            ps.setString(4, user.getDateofbirth());
//	            ps.setString(5, user.getGender());
//	            ps.setString(6, user.getLanguage());
//	            ps.setString(7, user.getEmail());
//	            ps.setString(8, user.getPassword());
//	            ps.setString(9, user.getAnswer1());
//	            ps.setString(10, user.getAnswer2());
//	            ps.executeUpdate();
//	            LOG.info("User data added in Database");
//		}
//		catch(Exception e)
//		{
//			LOG.fatal(e);
//		}
//		finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//	}
//	/*Get User id which is auto generated bt the database*/
//	public int getUserId(String mail)
//	{
//		int id=0;
//		try
//		{
//			con=ConnectionSetup.getConnection();
//			 ps=((java.sql.Connection) con).prepareStatement("select * from UserDetails where Email=?;");
//			 ps.setString(1,mail);
//		           ResultSet rs= ps.executeQuery();
//		           if(rs.next())
//		           {
//			           id=rs.getInt(1);
//			           LOG.info("Get UserID from Database");
//		           }
//		           rs.close();
//		}
//		catch(Exception e)
//		{
//			LOG.fatal(e);
//		}
//		finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//		}
//		return id;
//	}
//	/*Check The login user is valid or not*/
//	public User validUser(String email)
//	{
//		User user = null;
//		ArrayList<UserAddress> useradd;
//		ArrayList<UserImage> userimg;
//		
//        try 
//        {
//        	con=ConnectionSetup.getConnection();
//        	ps = con.prepareStatement("select * from UserDetails where Email = ?;");
//            ps.setString(1,email);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next())
//            {
//            	user = new User();
//            	useradd = (ArrayList<UserAddress>) addressdao.getUserAddress(rs.getInt(1));
//            	user.setAddress(useradd);
//            	userimg = (ArrayList<UserImage>) userImageDao.getUserImg(rs.getInt(1));
//            	user.setImage(userimg);
//            	user.setUserID(rs.getInt(1));
//            	user.setFirstname(rs.getString(2));
//            	user.setLastname(rs.getString(3));
//            	user.setPhone(rs.getLong(4));
//            	user.setDateofbirth(rs.getString(5));
//            	user.setGender(rs.getString(6));
//            	user.setLanguage(rs.getString(7));
//            	user.setEmail(rs.getString(8));
//            	user.setPassword(rs.getString(9));
//            	user.setAnswer1(rs.getString(10));
//            	user.setAnswer2(rs.getString(11));
//            	user.setRole(rs.getString(12));
//            }
//            LOG.info("User data stored in bean");
//        }
//        catch(Exception e)
//        {
//        	LOG.fatal(e);
//        }
//        finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//        return user;
//	}
//	/*Get user all details*/
//	public User getUserDetails(int userid)
//	{
//		User user = null;
//		ArrayList<UserAddress> useradd;
//		ArrayList<UserImage> userimg;
//		
//        try 
//        {
//        	con=ConnectionSetup.getConnection();
//        	ps = con.prepareStatement("select * from UserDetails where UserID = ?;");
//            ps.setInt(1,userid);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next())
//            {
//            	user = new User();
//            	useradd = (ArrayList<UserAddress>) addressdao.getUserAddress(rs.getInt(1));
//            	user.setAddress(useradd);
//            	userimg = (ArrayList<UserImage>) userImageDao.getUserImg(rs.getInt(1));
//            	user.setImage(userimg);
//            	user.setUserID(rs.getInt(1));
//            	user.setFirstname(rs.getString(2));
//            	user.setLastname(rs.getString(3));
//            	user.setPhone(rs.getLong(4));
//            	user.setDateofbirth(rs.getString(5));
//            	user.setGender(rs.getString(6));
//            	user.setLanguage(rs.getString(7));
//            	user.setEmail(rs.getString(8));
//            	user.setPassword(rs.getString(9));
//            	user.setAnswer1(rs.getString(10));
//            	user.setAnswer2(rs.getString(11));
//            	user.setRole(rs.getString(12));
//            }
//            LOG.info("Get Users details from Database");
//        }
//        catch(Exception e)
//        {
//        	LOG.fatal(e);
//        }
//        finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//        return user;
//	}
//	/*Get user role*/
//	public String getRole(String mail)
//	{
//		String role="";
//		try 
//        {
//        	con=ConnectionSetup.getConnection();
//        	ps = con.prepareStatement("select Role from UserDetails where Email = ?;");
//            ps.setString(1,mail);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next())
//            {
//            	role=rs.getString(1);
//            	LOG.info("Check Role Successful");
//            }
//        }
//		catch(Exception e)
//        {
//			LOG.fatal(e);
//        }
//        finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//		return role;
//	}


//	/*Deleting the user*/
//	public void deleteUser(int userid)
//	{
//		try
//		{
//				con=ConnectionSetup.getConnection();
//				ps=((java.sql.Connection) con).prepareStatement("delete from UserDetails where UserID=?;");
//			 	ps.setInt(1,userid);
//	            ps.executeUpdate();
//	            LOG.info("User deleted from Database");
//		}
//		catch(Exception e)
//		{
//			LOG.fatal(e);
//		}
//		finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//	}
//	/*Updating user profile*/
//	public void updateUserProfile(User user,int userid)
//	{
//		try
//		{
//			con=ConnectionSetup.getConnection();
//			 ps=((java.sql.Connection) con).prepareStatement("update UserDetails set FirstName=?,LastName=?,Phone=?,DateOfBirth=?,Gender=?,LanguageKnown=? where UserID=?;");
//			 
//			 	ps.setString(1, user.getFirstname());
//	            ps.setString(2, user.getLastname());
//	            ps.setLong(3, user.getPhone());
//	            ps.setString(4, user.getDateofbirth());
//	            ps.setString(5, user.getGender());
//	            ps.setString(6, user.getLanguage());
//	            ps.setInt(7, userid);
//	            ps.executeUpdate();
//	            LOG.info("User data updated in Database");
//		}
//		catch(Exception e)
//		{
//			LOG.fatal(e);
//		}
//		finally {
//			   try {
//					   if(ps!=null)
//					   {
//					       ps.close();
//				       }
//			   }catch (Exception e) {
//				   LOG.fatal(e);
//				}
//			   }
//	}
	
}
