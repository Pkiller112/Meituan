package edu.cn.pjh.model;

import java.util.Date;

public class BeanRider {
	private int riderid;
	private String ridername;
	private String riderStatus;
	private String ridersex;
	private Date riderborn;
	private Date registerTime;
	public String getRidersex() {
		return ridersex;
	}
	public void setRidersex(String ridersex) {
		this.ridersex = ridersex;
	}
	public Date getRiderborn() {
		return riderborn;
	}
	public void setRiderborn(Date riderborn) {
		this.riderborn = riderborn;
	}
	public int getRiderid() {
		return riderid;
	}
	public void setRiderid(int riderid) {
		this.riderid = riderid;
	}
	public String getRidername() {
		return ridername;
	}
	public void setRidername(String ridername) {
		this.ridername = ridername;
	}
	public String getRiderStatus() {
		return riderStatus;
	}
	public void setRiderStatus(String riderStatus) {
		this.riderStatus = riderStatus;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
}
