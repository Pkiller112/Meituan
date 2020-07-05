package edu.cn.pjh.model;

import java.util.Date;

public class BeanOrder {
	private String orderBarcade;
	private int businessid;
	private int consumerid;
	private int riderid;
	private float originPrice;
	private float finalPrice;
	private int schemeid;
	private int couponid;
	private Date createTime;
	private Date askTime;
	private int addressid;
	private String orderstatus;
	public String getOrderBarcade() {
		return orderBarcade;
	}
	public void setOrderBarcade(String orderBarcade) {
		this.orderBarcade = orderBarcade;
	}
	public int getBusinessid() {
		return businessid;
	}
	public void setBusinessid(int businessid) {
		this.businessid = businessid;
	}
	public int getConsumerid() {
		return consumerid;
	}
	public void setConsumerid(int consumerid) {
		this.consumerid = consumerid;
	}
	public int getRiderid() {
		return riderid;
	}
	public void setRiderid(int riderid) {
		this.riderid = riderid;
	}
	public float getOriginPrice() {
		return originPrice;
	}
	public void setOriginPrice(float originPrice) {
		this.originPrice = originPrice;
	}
	public float getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}
	public int getSchemeid() {
		return schemeid;
	}
	public void setSchemeid(int schemeid) {
		this.schemeid = schemeid;
	}
	public int getCouponid() {
		return couponid;
	}
	public void setCouponid(int couponid) {
		this.couponid = couponid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getAskTime() {
		return askTime;
	}
	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}
	public int getAddressid() {
		return addressid;
	}
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
}
