package edu.cn.pjh.model;

import java.util.Date;

public class BeanUser {
	private int userbarcade;
	public int getUserbarcade() {
		return userbarcade;
	}
	public void setUserbarcade(int userbarcade) {
		this.userbarcade = userbarcade;
	}
	private String userid;
	private String username;
	private String userpwd;
	private Date registerTime;
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String string) {
		this.userid = string;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
}
