package edu.cn.pjh.model;

import java.util.Date;

public class BeanConsumerCoupon {
	private int consumerid;
	private int couponid;
	private int businessid;
	private int couponNum;
	private int aleadyTimes;
	public int getConsumerid() {
		return consumerid;
	}
	public void setConsumerid(int consumerid) {
		this.consumerid = consumerid;
	}
	public int getCouponid() {
		return couponid;
	}
	public void setCouponid(int couponid) {
		this.couponid = couponid;
	}
	public int getBusinessid() {
		return businessid;
	}
	public void setBusinessid(int businessid) {
		this.businessid = businessid;
	}
	public int getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(int couponNum) {
		this.couponNum = couponNum;
	}
	public int getAleadyTimes() {
		return aleadyTimes;
	}
	public void setAleadyTimes(int aleadyTimes) {
		this.aleadyTimes = aleadyTimes;
	}
}
