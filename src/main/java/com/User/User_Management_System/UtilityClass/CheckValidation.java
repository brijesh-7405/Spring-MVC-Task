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

}
