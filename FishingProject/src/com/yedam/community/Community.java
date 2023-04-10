package com.yedam.community;

import java.sql.Date;

public class Community {
//	CO_NUM     NOT NULL NUMBER(5)      
//	NICK_NAME           VARCHAR2(30)   
//	TITLE      NOT NULL VARCHAR2(60)   
//	CONTENT    NOT NULL VARCHAR2(2000) 
//	WRITE_DATE          DATE           
//	VIEWS               NUMBER(4)      
//	RECOMMAND           NUMBER(4)
	
	private int coNum;
	private String nickName;
	private String title;
	private String content;
	private Date writeDate;
	private int views;
	private int recommand;
	
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getRecommand() {
		return recommand;
	}
	public void setRecommand(int recommand) {
		this.recommand = recommand;
	}
	
	
}
