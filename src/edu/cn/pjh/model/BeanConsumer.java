package edu.cn.pjh.model;

import java.util.Date;

public class BeanConsumer {
	private int consumerid;
	private String consumername;
	private String consumersex;
	private String consumeraccount;//ук╨е
	private String consumerpwd;
	private String consumerphone;
	private String ifvip;
	private String vipDDL;
	private Date registerTime;
	public int getConsumerid() {
		return consumerid;
	}
	public void setConsumerid(int consumerid) {
		this.consumerid = consumerid;
	}
	public String getConsumername() {
		return consumername;
	}
	public void setConsumername(String consumername) {
		this.consumername = consumername;
	}
	public String getConsumersex() {
		return consumersex;
	}
	public void setConsumersex(String consumersex) {
		this.consumersex = consumersex;
	}
	public String getConsumeraccount() {
		return consumeraccount;
	}
	public void setConsumeraccount(String consumeraccount) {
		this.consumeraccount = consumeraccount;
	}
	public String getConsumerpwd() {
		return consumerpwd;
	}
	public void setConsumerpwd(String consumerpwd) {
		this.consumerpwd = consumerpwd;
	}
	public String getConsumerphone() {
		return consumerphone;
	}
	public void setConsumerphone(String consumerphone) {
		this.consumerphone = consumerphone;
	}
	public String getIfvip() {
		return ifvip;
	}
	public void setIfvip(String ifvip) {
		this.ifvip = ifvip;
	}
	public String getVipDDL() {
		return vipDDL;
	}
	public void setVipDDL(String vipDDL) {
		this.vipDDL = vipDDL;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
}
