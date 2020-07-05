package edu.cn.pjh.model;
import java.util.Date;

public class BeanAppraise {
	private int appraiseid;
	private int productid;
	private int consumerid;
	private String content;
	private Date time;
	private int star;
	public int getAppraiseid() {
		return appraiseid;
	}
	public void setAppraiseid(int appraiseid) {
		this.appraiseid = appraiseid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public int getConsumerid() {
		return consumerid;
	}
	public void setConsumerid(int consumerid) {
		this.consumerid = consumerid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
}
