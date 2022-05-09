package com.User.User_Management_System.Bean;

import java.io.InputStream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
/* UserImage Bean Class*/
@Entity
public class UserImage {
	@Transient
	private int userid;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int imgid;
	private byte[] imgbytes;
	public byte[] getImgbytes() {
		return imgbytes;
	}
	public void setImgbytes(byte[] imgbytes) {
		this.imgbytes = imgbytes;
	}
	private String base64Image;
	@ManyToOne
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public int getImgid() {
		return imgid;
	}
	public void setImgid(int imgid) {
		this.imgid = imgid;
	}	
}
