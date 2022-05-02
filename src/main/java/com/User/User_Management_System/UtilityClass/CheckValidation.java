package com.User.User_Management_System.UtilityClass;

import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CheckValidation {
	static final Logger LOG = LogManager.getLogger(CheckValidation.class.getName());
	private  transient  String regex = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";
	private transient  String mailFormat="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	public boolean validatename(String name)
	{
		if(Pattern.matches("[a-zA-Z]+",name))
		{
			LOG.debug("Character Validation Pass");
			return false;
		}
		else
		{
			LOG.debug("Character Validation Fail");
			return true;
		}
	}
	public boolean validatepwd(String pwd)
	{
		if(Pattern.matches(regex,pwd))
		{
			LOG.debug("Password Validation Pass");
			return false;
		}
		else
		{
			LOG.debug("Password Validation Fails");
			return true;
		}
	}
	public boolean validatemail(String mail)
	{
		if(Pattern.matches(mailFormat,mail))
		{
			LOG.debug("Mail Validation Pass");
			return false;
		}
		else
		{
			LOG.debug("Mail Validation Fail");
			return true;
		}
	}
	public boolean validateNumber(String number)
	{
		 String numbers = "^[0-9]+$";
		if(number.matches(numbers))
		{
			LOG.debug("Number Validation Pass");
			return false;
		}
		else
		{
			LOG.debug("Number Validation Fails");
			return true;
		}
	}

}
