package com.User.User_Management_System.Dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.UtilityClass.ConnectionSetup;

public class UserImageDaoImpl implements UserImageDao{
	static final Logger LOG = LogManager.getLogger(UserImageDaoImpl.class.getName());
	private transient Connection con = null;
	private transient PreparedStatement ps=null;
	/* Add User's images into the database*/
	public void addUserImage(UserImage img)
	{
		try
		{
				con=ConnectionSetup.getConnection();
				ps=((java.sql.Connection) con).prepareStatement("insert into UserImages(UserID,Image) values(?,?);");
			 	ps.setInt(1, img.getUserid());
	            ps.setBlob(2, img.getImage());
	            ps.executeUpdate();
	            LOG.info("Images added in Database");
		}
		catch(Exception e)
		{
			LOG.fatal(e);
		}
		finally {
			   try {
					   if(ps!=null)
					   {
					       ps.close();
				       }
			   }catch (Exception e) {
				   LOG.fatal(e);
				}
			   }
	}
	/* Getting User's images from the database*/
	public List<UserImage> getUserImg(int userid)
	{
		List<UserImage> userimg = new ArrayList<UserImage>();
		ByteArrayOutputStream outputStream;
		UserImage user;
		byte[] buffer ;
		try
		 {
			java.sql.Blob image=null;
			con=ConnectionSetup.getConnection();
		  ps=((java.sql.Connection) con).prepareStatement("select * from UserImages where UserID=?;"); 
		  ps.setInt(1,userid);
         ResultSet rs=ps.executeQuery();  
         while(rs.next()){  
        	 image=rs.getBlob(3);
        	 InputStream inputStream = image.getBinaryStream();
        	  outputStream = new ByteArrayOutputStream();
        	  buffer = new byte[4096];
        	 int bytesRead = -1;
        	  
        	 while ((bytesRead = inputStream.read(buffer)) != -1) {
        	     outputStream.write(buffer, 0, bytesRead);
        	 }
        	 byte[] imageBytes = outputStream.toByteArray();
        	  
        	 String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        	 user=new UserImage();
        	 user.setBase64Image(base64Image);
        	 user.setImgid(rs.getInt(2));
	        	userimg.add(user); 
	        	LOG.info("Images retrieve from Database");
         }
         rs.close();
		 } catch (Exception e) {
			 LOG.fatal(e);
			}
		 finally {
			   try {
					   if(ps!=null)
					   {
					       ps.close();
				       }
			   }catch (Exception e) {
				   LOG.fatal(e);
				}
			   }
		return userimg;
	}
	/*Delete Image*/
	public void deleteImage(int imgid)
	{
		try
		{
				con=ConnectionSetup.getConnection();
				ps=((java.sql.Connection) con).prepareStatement("delete from UserImages where ImageID=?;");
			 	ps.setInt(1,imgid);
	            ps.executeUpdate();
	            LOG.info("Images deleted from Database");
		}
		catch(Exception e)
		{
			LOG.fatal(e);
		}
		finally {
			   try {
					   if(ps!=null)
					   {
					       ps.close();
				       }
			   }catch (Exception e) {
				   LOG.fatal(e);
				}
			   }
	}
}
