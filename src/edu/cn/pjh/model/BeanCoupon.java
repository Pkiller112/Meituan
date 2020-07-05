package edu.cn.pjh.model;

import java.util.Date;

public class BeanCoupon {
	private int businessid;
	private int couponid;
	private float reduce;
	private int needtime;
	private Date beginTime;
	private Date deadTime;
	public int getBusinessid() {
		return businessid;
	}
	public void setBusinessid(int businessid) {
		this.businessid = businessid;
	}
	public int getCouponid() {
		return couponid;
	}
	public void setCouponid(int couponid) {
		this.couponid = couponid;
	}
	public float getReduce() {
		return reduce;
	}
	public void setReduce(float reduce) {
		this.reduce = reduce;
	}
	public int getNeedtime() {
		return needtime;
	}
	public void setNeedtime(int needtime) {
		this.needtime = needtime;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}
}
