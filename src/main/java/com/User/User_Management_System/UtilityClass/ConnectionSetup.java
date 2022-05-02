package com.User.User_Management_System.UtilityClass;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConnectionSetup {
	static final Logger LOG = LogManager.getLogger(ConnectionSetup.class.getName());
	public final static Connection getConnection() throws SQLException,ClassNotFoundException, IOException
	   {
		   Connection connection=null;
		   FileInputStream reader=null;
	   try {
		   Class.forName("com.mysql.cj.jdbc.Driver");
		   String resourceName = "database.properties"; // could also be a constant
		   ClassLoader loader = Thread.currentThread().getContextClassLoader();
		   Properties p = new Properties();
		   try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
		       p.load(resourceStream);
		   }
		    connection =(Connection) DriverManager.getConnection(p.getProperty("url"),p.getProperty("user"),p.getProperty("password"));
	   } 
	   catch(SQLException e)
	   {
		   LOG.fatal(e);
		}
	   finally {
           
           if(reader!=null)
           {
        	   reader.close();
           }
	   }
	   LOG.info("Connection setup Successfull");
	   return connection;
	   }
}
