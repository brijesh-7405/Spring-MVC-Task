package com.User.User_Management_System.Bean;
import java.util.*;

//User Bean Class
public class User {
	private int userID;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	private String firstname;
	private String lastname;
	private String email;
	private long phone;
	private String password;
	private String dateofbirth;
	private String gender;
	private String language;
	private String role;
	private ArrayList<UserAddress> address;
	private ArrayList<UserImage>  image;
 	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	private String answer1;
	private String answer2;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public ArrayList<UserAddress> getAddress() {
		return new ArrayList<UserAddress>(this.address);
	}
	public void setAddress(ArrayList<UserAddress> address) {
		this.address = new ArrayList<UserAddress>(address);
	}
	public ArrayList<UserImage> getImage() {
		return new ArrayList<UserImage> (this.image);
	}
	public void setImage(ArrayList<UserImage> image) {
		this.image =  new ArrayList<UserImage>(image);
	}

}
