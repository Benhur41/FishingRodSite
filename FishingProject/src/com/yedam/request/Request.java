package com.yedam.request;


public class Request {
//	NUM               NUMBER(10)   
//	NICK_NAME         VARCHAR2(30) 
//	DISCOUNT_PRICE    NUMBER(10)   
//	RP_NUM            NUMBER(10)   
//	STATE             CHAR(1)   
	
	private int num;
	private String nickName;
	private String repair;
	private double discountPrice;
	private int rpNum;
	private String state;
	private int count;
	private String fishingRod;
	private double sales;
	
	
	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}
	public String getFishingRod() {
		return fishingRod;
	}
	public void setFishingRod(String fishingRod) {
		this.fishingRod = fishingRod;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getRepair() {
		return repair;
	}
	public void setRepair(String repair) {
		this.repair = repair;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public int getRpNum() {
		return rpNum;
	}
	public void setRpNum(int rpNum) {
		this.rpNum = rpNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
