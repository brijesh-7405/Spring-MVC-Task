package com.User.User_Management_System.Filter;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
@MultipartConfig
@WebFilter(urlPatterns={"/LoginServlet","/ResetPassword","/UserRegistration"})
public class EncryptPwd implements Filter {
	static final Logger LOG = LogManager.getLogger(EncryptPwd.class.getName());
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 String password=request.getParameter("password");
	        String encryptedpassword = null;  
	        try   
	        {  
	            MessageDigest m = MessageDigest.getInstance("MD5");  
	              
	            /* Add plain-text password bytes to digest using MD5 update() method. */  
	           m.update(password.getBytes("UTF-8"));  
	              
	            /* Convert the hash value into bytes */   
	            byte[] bytes = m.digest();  
	              
	            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */  
	            StringBuilder s = new StringBuilder();  
	            for(int i=0; i< bytes.length ;i++)  
	            {  
	                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
	            }  
	              
	            encryptedpassword = s.toString();  
	            request.setAttribute("password",encryptedpassword);
	        	chain.doFilter(request, response);
	        }   
	        catch (Exception e)   
	        {  
	        	LOG.fatal(e);
	        }  
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
