package com.home.login;

import com.home.common.DAO;

public class FishDAO extends DAO {
	
	private static FishDAO fishDao = null;
	
	private FishDAO () {
		
	}
	
	public static FishDAO getInstance() {
		if(fishDao == null) {
			fishDao = new FishDAO();
		}
		return fishDao;
	}
	
	//회원가입 기능
	public int register(FishUser fu) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO fishuser VALUES "
						+ " (?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fu.getId());
			pstmt.setString(2, fu.getPw());
			pstmt.setString(3, fu.getName());
			pstmt.setString(4, fu.getAddress());
			pstmt.setString(5, fu.getPhoneNum());
			pstmt.setString(6, fu.getFishingRod1());
			pstmt.setString(7, fu.getFishingRod2());
			pstmt.setString(8, fu.getFishingRod3());
			pstmt.setString(9, fu.getFishingRod4());
			pstmt.setString(10, fu.getFishingRod5());
			
			result = pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	
	//로그인 기능
	public FishUser login(String id) {
		FishUser fishUser = null;
		
		try {
			conn();
			String sql = "SELECT * FROM fishuser WHERE id =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fishUser = new FishUser();
				fishUser.setId(rs.getString("id"));
				fishUser.setPw(rs.getString("pw"));
				fishUser.setName(rs.getString("name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return fishUser;
	}
}
