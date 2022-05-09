package com.User.User_Management_System.Bean;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//User Bean Class
@Entity
@Component
@Scope("prototype")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<UserAddress> address;
//	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//	private List<UserImage>  image;
//	
//public List<UserImage> getImage() {
//		return image;
//	}
//	public void setImage(List<UserImage> image) {
//		this.image = image;
//	}
public User() {
		//super();
		// TODO Auto-generated constructor stub
	}
public List<UserAddress> getAddress() {
		return address;
	}
	public void setAddress(List<UserAddress> address) {
		this.address = address;
	}
//	private ArrayList<UserImage>  image;
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

}
