package com.User.User_Management_System.UtilityClass;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;

import org.springframework.beans.factory.annotation.Autowired;

import com.User.User_Management_System.Bean.User;
import com.User.User_Management_System.Bean.UserAddress;
import com.User.User_Management_System.Service.UserService;

public class Validation {
@Autowired
private CheckValidation val;
@Autowired
private UserService userservice;
	@SuppressWarnings({ "null", "unused" })
	public String validData(User user,String repwd) {
		
//		user.setFirstname(fname);
//		user.setLastname(lname);
//		user.setEmail(email);
//		if(val.validateNumber(phone))
//		{
//			request.setAttribute("phonenumber", phone);
//		}
//		else {
//		 user.setPhone(Long.parseLong(phone));
//		}
//		user.setPassword(pwd);
//		user.setDateofbirth(birthdate);
//		user.setAnswer1(ans1);
//		user.setAnswer2(ans2);
//		user.setGender(gender);
//		String lang="";
//		if(language!=null)
//		{
//			StringBuffer buf = new StringBuffer();
//			for(int i=0;i< language.length;i++){
//				buf.append(language[i]);
//			}
//			lang=buf.toString();
//		}
//		user.setLanguage(lang);
//		UserAddress useraddress;
//		ArrayList<UserAddress> list = new ArrayList<UserAddress>();
//		if(address1!=null)
//		{
//			for(int i=0;i<address1.length;i++)
//			{
//					useraddress= new UserAddress();
//					useraddress.setAdd1(address1[i]);
//					useraddress.setAdd2(address2[i]);
//					useraddress.setPincode(pincode[i]);
//					useraddress.setCity(city[i]);
//					useraddress.setState(state[i]);
//					useraddress.setCountry(country[i]);
//					list.add(useraddress);
//			}
//		}
//		user.setAddress(list);
//		RequestDispatcher rd=request.getRequestDispatcher("registration.jsp"); 
//		request.setAttribute("faildata", user);
		
		String fname=user.getFirstname();
		String lname=user.getLastname();
		String email=user.getEmail();
		long phone=user.getPhone();
		String pwd=user.getPassword();
		String birthdate=user.getDateofbirth();
		String ans1=user.getAnswer1();
		String ans2=user.getAnswer2();
		String gender=user.getGender();
		String language = user.getLanguage();
		
		boolean validate=true;
		String message="";
		
		List<UserAddress> addresses= user.getAddress();
		//addresses.stream().forEach(address->{
			for(UserAddress address:addresses)
			{
			if(address.getAdd1().equals("")||address.getAdd2().equals("")||address.getCity().equals("")||address.getCountry().equals("")||address.getPincode().equals("")||address.getState().equals(""))
			{
				message="*All Feilds are Required";
				validate=false;
			}
			if(address.getPincode()!=null)
			{
				for(int i=0;i<address.getPincode().length();i++)
				{
					if(val.validateNumber(address.getPincode()))
					{
						message="*Only Numbers are Allowed in pincode.";
						validate=false;
						break;
					}
					else if(address.getPincode().length()>6)
					{
						message="*Pincode not greater than 6 Digits.";
						validate=false;
						break;
					}
					else if(address.getPincode().length()>255 || address.getPincode().length()>255)
					{
						message="*Address length exceeded.";
						validate=false;
						break;
					}
				}
			}
			}
		
		
		if(fname.equals("")||pwd.equals("")||lname.equals("")||email.equals("")||phone==0||repwd.equals("")||birthdate.equals("")||ans1.equals("")||ans2.equals("")||gender.equals(""))
		{
			message="*All Feilds are Required";
			validate=false;
		}
		else if(val.validatename(fname))
		{
			message="*Only Alphabets are Allowed in FirstName.";
			validate=false;
		}
		else if(val.validatename(lname))
		{
			message="*Only Alphabets are Allowed in LastName.";
			validate=false;
		}
		else if(val.validatepwd(pwd))
		{
			message="*Please Choose Strong Password.";
			validate=false;
		}
		else if(!pwd.equals(repwd))
		{
			message="*Confirm password Should be same as Password.";
			validate=false;
		}
		else if(val.validateNumber(String.valueOf(phone)))
		{
			message="*Only Numbers are Allowed in Phone.";
			validate=false;
		}
		else if(String.valueOf(phone).length()<10)
		{
			message="*Number not less than 10 Digits.";
			validate=false;
		}
		else if(val.validatemail(email))
		{
			message="*Please Enter Valid Mail-Id.";
			validate=false;
		}
		else if(userservice.userExist(email))
		{
			message="*Email Already exist.";
			validate=false;
		}
		
		
		if(validate==true)
		{
			message="validData";
		}

		return message;
			}
	}

