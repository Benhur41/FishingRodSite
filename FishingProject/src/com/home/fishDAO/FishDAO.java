package com.home.fishDAO;

import java.util.ArrayList;
import java.util.List;

import com.home.common.DAO;
import com.home.exe.FishExe;

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
						+ " (?,?,?,?,?,?,'D',0,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fu.getId());
			pstmt.setString(2, fu.getPw());
			pstmt.setString(3, fu.getNickName());
			pstmt.setString(4, fu.getName());
			pstmt.setString(5, fu.getAddress());
			pstmt.setString(6, fu.getCustomerPhone());
//			pstmt.setString(7, fu.getCustomerGrade());
			pstmt.setString(7, fu.getFishingRod1());
			pstmt.setString(8, fu.getFishingRod2());
			pstmt.setString(9, fu.getFishingRod3());
			pstmt.setString(10, fu.getFishingRod4());
			pstmt.setString(11, fu.getFishingRod5());
			
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
				fishUser.setCustomerGrade(rs.getString("customer_grade"));
				fishUser.setNick_name(rs.getString("nick_name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return fishUser;
	}
	//닉네임 확인
	public FishUser checkNick(String nickName) {
		FishUser fishUser = null;
		
		try {
			conn();
			String sql = "SELECT * FROM fishuser WHERE nick_name =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fishUser = new FishUser();
				fishUser.setId(rs.getString("id"));
				fishUser.setPw(rs.getString("pw"));
				fishUser.setName(rs.getString("name"));
				fishUser.setNick_name(rs.getString("nick_name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return fishUser;
	}
	
	//커뮤니티 게시판 글 삭제( 관계된 댓글도 삭제 )
	public int deleteTwo(int no) {
		int result = 0;
		int a = no;
		try {
			conn();
			String sql = "DELETE FROM test WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			if(result > 0 ) {//삭제성공시 글 숫자 연속되게 만들어주기
//				String sql2 = "CREATE or REPLACE TRIGGER update_co_num\r\n"
//							+ "AFTER UPDATE OF co_num ON test\r\n"
//							+ "FOR EACH ROW\r\n"
//							+ "BEGIN \r\n"
//							+ "    UPDATE test2\r\n"
//							+ "    SET co_num = :new.co_num\r\n"
//							+ "    WHERE co_num = :old.co_num;\r\n"
//							+ "END;\r\n"
//							+ "/\r\n";
//				pstmt = conn.prepareStatement(sql2);
//				pstmt.executeQuery();
				String sql3 = "update test set co_num = co_num -1 WHERE co_num > ?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, a);
				result = pstmt.executeUpdate();
//				String sql4 ="drop trigger update_co_num";
//				pstmt = conn.prepareStatement(sql4);
//				result = pstmt.executeUpdate();
			}else {//삭제실패시
				System.out.println("삭제 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	//회원 삭제 기능 ( 글 불연속 번호 갱신 기능까지 같이 넣어야함 ! )
	//회원 정보 수정 기능
	public int fishUserUpdate(FishUser fu,int no) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE fishuser SET ? = ? WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			switch(no) {
			case 1: 
				pstmt.setString(1, "pw");
				break;
			case 2:
				pstmt.setString(1, "nick_name");
				break;
			case 3:
				pstmt.setString(1, "name");
				break;
			case 4:
				pstmt.setString(1, "address");
				break;
			case 5:
				pstmt.setString(1, "customer_phone");
				break;
			case 6:
				pstmt.setString(1, "fishing_rod1");
				break;
			case 7:
				pstmt.setString(1, "fishing_rod2");
				break;
			case 8:
				pstmt.setString(1, "fishing_rod3");
				break;
			case 9:
				pstmt.setString(1, "fishing_rod4");
				break;
			case 10:
				pstmt.setString(1, "fishing_rod5");
				break;
			}
			switch(no) {
			case 1: 
				pstmt.setString(2, fu.getPw());
				break;
			case 2:
				pstmt.setString(2, fu.getNickName());
				break;
			case 3:
				pstmt.setString(2, fu.getName());
				break;
			case 4:
				pstmt.setString(2, fu.getAddress());
				break;
			case 5:
				pstmt.setString(2, fu.getCustomerPhone());
				break;
			case 6:
				pstmt.setString(2, fu.getFishingRod1());
				break;   
			case 7:      
				pstmt.setString(2, fu.getFishingRod2());
				break;   
			case 8:      
				pstmt.setString(2, fu.getFishingRod3());
				break;   
			case 9:      
				pstmt.setString(2, fu.getFishingRod4());
				break;   
			case 10:     
				pstmt.setString(2, fu.getFishingRod5());
				break;
			}
			pstmt.setString(3, FishExe.fishUserInfo.getId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	//낚싯대 정보 가져오기
	public List<FishUser> getRodList(){
		List<FishUser> list = new ArrayList<>();
		FishUser fishuser = null;
			try {
			conn();
			String sql = " SELECT fishing_Rod1,fishing_Rod2,fishing_Rod3,fishing_Rod4,fishing_Rod5 FROM fishuser";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				fishuser = new FishUser();
				fishuser.setFishingRod1(rs.getString("fishing_rod1"));
				fishuser.setFishingRod2(rs.getString("fishing_rod2"));
				fishuser.setFishingRod3(rs.getString("fishing_rod3"));
				fishuser.setFishingRod4(rs.getString("fishing_rod4"));
				fishuser.setFishingRod5(rs.getString("fishing_rod5"));
				list.add(fishuser);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	//회원 전체 조회
	public List<FishUser> getUserList(){
		List<FishUser> list = new ArrayList<>();
		FishUser fishUser = null;
		try {
			conn();
			String sql = "SELECT * FROM fishuser";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fishUser = new FishUser();
				fishUser.setId(rs.getString("id"));
				fishUser.setPw(rs.getString("pw"));
				fishUser.setNick_name(rs.getString("nick_name"));
				fishUser.setName(rs.getString("name"));
				fishUser.setCustomerPhone(rs.getString("customer_phone"));
				fishUser.setCustomerGrade(rs.getString("customer_grade"));
				fishUser.setRepairCount(rs.getInt("repair_count"));
				fishUser.setFishingRod1(rs.getString("fishing_rod1"));
				fishUser.setFishingRod2(rs.getString("fishing_rod2"));
				fishUser.setFishingRod3(rs.getString("fishing_rod3"));
				fishUser.setFishingRod4(rs.getString("fishing_rod4"));
				fishUser.setFishingRod5(rs.getString("fishing_rod5"));
				list.add(fishUser);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	
	}

