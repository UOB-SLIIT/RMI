package com.perisic.beds.others;
/**
 * Serializable UserDetails class
 * 
 * @author Nuwantha Fernando
 *
 */
import java.io.Serializable;

public class UserDetails implements Serializable {
	private String user_nic;
	private String user_firstname;
	private String user_lastname;
	private String user_fullname=user_firstname+" "+user_lastname;
	private String user_address;
	private int user_phoneNo;
	private String user_email;
	private String user_feedback;
	private long user_billNo;
	private String user_type;
	
	//Getters and Setters 
	public String getUser_nic() {
		return user_nic;
	}
	public void setUser_nic(String user_nic) {
		this.user_nic = user_nic;
	}
	public String getUser_firstname() {
		return user_firstname;
	}
	public void setUser_firstname(String user_firstname) {
		this.user_firstname = user_firstname;
	}
	public String getUser_lastname() {
		return user_lastname;
	}
	public void setUser_lastname(String user_lastname) {
		this.user_lastname = user_lastname;
	}
	public String getUser_fullname() {
		return user_fullname;
	}
	public void setUser_fullname(String user_fullname) {
		this.user_fullname = user_fullname;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public int getUser_phoneNo() {
		return user_phoneNo;
	}
	public void setUser_phoneNo(int user_phoneNo) {
		this.user_phoneNo = user_phoneNo;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_feedback() {
		return user_feedback;
	}
	public void setUser_feedback(String user_feedback) {
		this.user_feedback = user_feedback;
	}
	public long getUser_billNo() {
		return user_billNo;
	}
	public void setUser_billNo(long user_billNo) {
		this.user_billNo = user_billNo;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	
	
}
