package com.yedam.comments;

import java.sql.Date;

public class Comments {
//	CO_NUM        NUMBER(5)      
//	NICK_NAME     VARCHAR2(30)   
//	CONTENT       VARCHAR2(1000) 
//	WRITE_DATE    DATE           
//	RECOMMAND     NUMBER(4) 
	
	private int coNum;
	private int num;
	private String nickName;
	private String content;
	private Date writeDate;
	private int recommand;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCoNum() {
		return coNum;
	}
	public void setCoNum(int coNum) {
		this.coNum = coNum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public int getRecommand() {
		return recommand;
	}
	public void setRecommand(int recommand) {
		this.recommand = recommand;
	}
	
	
}
