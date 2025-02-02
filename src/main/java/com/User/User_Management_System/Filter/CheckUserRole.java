package com.User.User_Management_System.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;

@WebFilter(urlPatterns = {"/adminDashBoard","/adminWork"})
public class CheckUserRole implements Filter {
	static final Logger LOG = LogManager.getLogger(CheckUserRole.class.getName());
	public void destroy() {	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		LOG.info("Check role of user Filter");
		HttpServletRequest httpReq = (HttpServletRequest) request;
	    HttpSession session = httpReq.getSession(false);
	    if(session!=null)
	    {
	    	User user = (User) session.getAttribute("USER");
	    		if(user.getRole().equals("admin"))
				{
			    	chain.doFilter(request, response);
				}
			    else
			    {
			    	((HttpServletResponse) response).sendRedirect("userDashBoard");
			    }
	    }
	    else
	    {
	    	((HttpServletResponse) response).sendRedirect("index");
	    }
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
