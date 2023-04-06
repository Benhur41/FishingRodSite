package com.home.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {
	
	protected Connection conn = null;
	protected ResultSet rs = null;
	protected PreparedStatement pstmt = null;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	String id = "hr";
	String pw = "hr";
	
	public void conn () {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconn() {
		try {
			if(conn != null) {
				conn.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(pstmt !=null) {
				pstmt.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}