package com.User.User_Management_System.Bean;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/*UserAddress Bean Class*/
@Entity
public class UserAddress implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int addressid;
	@NotEmpty(message="*Address feild is required")
	private String add1;
	@NotEmpty(message="*Address feild is required")
	private String add2;
	@NotEmpty(message="*City is required")
	private String city;
	@NotEmpty(message="*State is required")
	private String state;
	@NotEmpty(message="*Country is required")
	private String country;
	@NotEmpty(message="*Pincode is required")
	@Pattern(regexp="^[0-9]+$",message="*Only Numbers are Allowed in Pincode.")
	private String pincode;
	@ManyToOne
	private User user;
	public UserAddress() {}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public int getAddressid() {
		return addressid;
	}
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}
	
	
	@Override
	public String toString() {
		return "UserAddress [addressid=" + addressid + ", add1=" + add1 + ", add2=" + add2
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", pincode=" + pincode + "]";
	}
}
