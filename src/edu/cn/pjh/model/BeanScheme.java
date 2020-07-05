package edu.cn.pjh.model;

public class BeanScheme {
	private int businessid;
	private int schemeid;
	private float needprice;
	private float reduce;
	private String ifcanusecoupon;
	public int getBusinessid() {
		return businessid;
	}
	public void setBusinessid(int businessid) {
		this.businessid = businessid;
	}
	public int getSchemeid() {
		return schemeid;
	}
	public void setSchemeid(int schemeid) {
		this.schemeid = schemeid;
	}
	public float getNeedprice() {
		return needprice;
	}
	public void setNeedprice(float needprice) {
		this.needprice = needprice;
	}
	public float getReduce() {
		return reduce;
	}
	public void setReduce(float reduce) {
		this.reduce = reduce;
	}
	public String getIfcanusecoupon() {
		return ifcanusecoupon;
	}
	public void setIfcanusecoupon(String ifcanusecoupon) {
		this.ifcanusecoupon = ifcanusecoupon;
	}
}
