package edu.cn.pjh.model;
public class BeanProduct {
	private int productid;
	private int typeid;
	private String productname;
	private float product_price;
	private float product_vipprice;
	private int num;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public float getProduct_price() {
		return product_price;
	}
	public void setProduct_price(float product_price) {
		this.product_price = product_price;
	}
	public float getProduct_vipprice() {
		return product_vipprice;
	}
	public void setProduct_vipprice(float product_vipprice) {
		this.product_vipprice = product_vipprice;
	}
}
