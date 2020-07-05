package edu.cn.pjh.model;

import java.util.Date;

public class BeanRiderIncome {
	private String orderbarcade;
	private int riderid;
	private Date time;
	private String appraise;
	private float thisIncome;

	public String getOrderbarcade() {
		return orderbarcade;
	}
	public void setOrderbarcade(String orderbarcade) {
		this.orderbarcade = orderbarcade;
	}
	public int getRiderid() {
		return riderid;
	}
	public void setRiderid(int riderid) {
		this.riderid = riderid;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAppraise() {
		return appraise;
	}
	public void setAppraise(String appraise) {
		this.appraise = appraise;
	}
	public float getThisIncome() {
		return thisIncome;
	}
	public void setThisIncome(float thisIncome) {
		this.thisIncome = thisIncome;
	}
}
