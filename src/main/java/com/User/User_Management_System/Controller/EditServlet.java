package com.User.User_Management_System.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Bean.UserImage;
import com.User.User_Management_System.Service.UserAddressService;
import com.User.User_Management_System.Service.UserAddressServiceImpl;
import com.User.User_Management_System.Service.UserImageService;
import com.User.User_Management_System.Service.UserImageServiceImpl;
import com.User.User_Management_System.Service.UserService;
import com.User.User_Management_System.Service.UserServiceImpl;
@MultipartConfig
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LogManager.getLogger(EditServlet.class.getName());
	private transient  UserService userservice;
	private transient  UserAddressService userAddressService;
	private transient  UserImageService userImageService;
	public void init(ServletConfig config) throws ServletException {
		userservice = new UserServiceImpl();
		userAddressService = new UserAddressServiceImpl();
		userImageService = new UserImageServiceImpl();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String fname=request.getParameter("firstname");  
		String lname=request.getParameter("lastname"); 
		String phone=request.getParameter("phone");
		String birthdate=request.getParameter("birthdate");
		String gender=request.getParameter("Gender");
		String address1[]=request.getParameterValues("address1");
		String address2[]=request.getParameterValues("address2");
		String[] pincode=request.getParameterValues("pincode");
		String[] city=request.getParameterValues("city");
		String[] state=request.getParameterValues("state");
		String[] country=request.getParameterValues("country");
		String lang[]=request.getParameterValues("lang");
		String addressid[]=request.getParameterValues("addid");
		
		String language="";
		StringBuffer buf = new StringBuffer();
		if(lang!=null)
		{
			for(int i=0;i< lang.length;i++){
				buf.append(" "+lang[i]);
			}
			language=buf.toString();
		}
		long number = Long.parseLong(phone); 
		
		User user = new User();
		user.setFirstname(fname);
		user.setLastname(lname);
		user.setPhone(number);
		user.setDateofbirth(birthdate);
		user.setGender(gender);
		user.setLanguage(language);
		String uid=request.getParameter("userid");
		int userid=Integer.parseInt(uid);
		userservice.updateUserProfile(user,userid); //user profile updated
		
		List<UserAddress> useraddresses =userAddressService.getUserAddress(userid);
		int index=0;
		int oldAddressid[] = new int[useraddresses.size()];
		int addressIdLength= addressid.length;
		int count=0;
		for(UserAddress ud:useraddresses)
		{	
			oldAddressid[index]=ud.getAddressid();
			if(count<addressIdLength && addressid[count].length()!=0)
			{
				int addrssid=Integer.parseInt(addressid[count]);
				if(oldAddressid[index]==addrssid)
				{
					count++;
				}
				else
				{
					LOG.debug("Address deleted");
					userAddressService.deleteAddress(oldAddressid[index]);     //user Address deleted
				}
			}
			else
			{
				LOG.debug("Address deleted");
				userAddressService.deleteAddress(oldAddressid[index]);   //user Address deleted
			}
			index++;
		}
		UserAddress useraddress;
		for(int i=0;i<addressid.length;i++)
		{	
			useraddress = new UserAddress();
			if(addressid[i].length()==0 || addressid[i].equals("0"))     
			{
				//add new address of the user in address table
				useraddress.setUserid(userid);
				useraddress.setAdd1(address1[i]);
				useraddress.setAdd2(address2[i]);
				useraddress.setPincode(pincode[i]);
				useraddress.setCity(city[i]);
				useraddress.setState(state[i]);
				useraddress.setCountry(country[i]);
				userAddressService.addUserAddress(useraddress);
				LOG.debug("New Address added");
			}
			else
			{
				//Update  the User Address of the particular Address Id
 				int addrssid=Integer.parseInt(addressid[i]);
				useraddress.setUserid(userid);
				useraddress.setAddressid(addrssid);
				useraddress.setAdd1(address1[i]);
				useraddress.setAdd2(address2[i]);
				useraddress.setPincode(pincode[i]);
				useraddress.setCity(city[i]);
				useraddress.setState(state[i]);
				useraddress.setCountry(country[i]);
				userAddressService.updateUserAddress(useraddress);
				LOG.debug("Address Updated");
			}
		}
		//Image Addition to the user
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<Part> fileParts = (ArrayList) request.getParts().stream().filter(new Predicate<Part>() {
			public boolean test(Part part) {
			return "image[]".equals(part.getName()) && part.getSize() > 0;
		}
		}).collect(Collectors.toList()); 
	    InputStream inputStream;
		UserImage userimg;
        for (Part filePart : fileParts) {
            if (filePart != null && filePart.getSize() != 0) {
            	 userimg = new UserImage();
                inputStream = filePart.getInputStream();
                userimg.setUserid(userid);
                userimg.setImage(inputStream);
                userImageService.addUserImg(userimg);
                LOG.debug("New Images added");
            }
        }
       response.sendRedirect("UserData");   
	}

}
