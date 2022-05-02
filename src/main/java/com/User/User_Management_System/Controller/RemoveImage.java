package com.User.User_Management_System.Controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Service.UserImageService;
import com.User.User_Management_System.Service.UserImageServiceImpl;

public class RemoveImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(RemoveImage.class.getName());

	private transient  UserImageService userImageService;
	public void init(ServletConfig config) throws ServletException {
		userImageService = new UserImageServiceImpl();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BasicConfigurator.configure();
		response.setContentType("text/html");
		String imgid=request.getParameter("imgId"); 
		int imageid = Integer.parseInt(imgid);
		userImageService.deleteImage(imageid);         //Delete image from the database 
		LOG.debug("image-deleted");
	}
}

