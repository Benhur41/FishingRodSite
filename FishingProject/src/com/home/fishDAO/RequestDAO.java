package com.home.fishDAO;

import java.util.ArrayList;
import java.util.List;

import com.home.common.DAO;

public class RequestDAO extends DAO {
//	try {
//		conn();
//	}catch(Exception e) {
//		e.printStackTrace();
//	}finally {
//		disconn();
//	}
	private static RequestDAO requestDao = new RequestDAO();
	
	private RequestDAO () {
		
	}
	
	public static RequestDAO getInstance() {
		return requestDao;
	}
	
	//전체 조회
	public List<Request> getRequstList () {
		List<Request> list = new ArrayList<>();
		Request request = null;
		try {
			conn();
			String sql = "SELECT num, nick_name, repair, decode(state , 'R' , '수리중',\r\n"
					+ "                                              'P' , '배송중',\r\n"
					+ "                                                    '수리전') \"state\",decode(customer_grade,'A',price*0.9,\r\n"
					+ "                                                             'B',price*0.95,\r\n"
					+ "                                                             'C',price*0.97,\r\n"
					+ "                                                                 price) \"discount_price\"\r\n"
					+ "FROM fishuser \r\n"
					+ "join request using (nick_name)\r\n"
					+ "join repair using (rp_num)\r\n"
					+ "WHERE nick_name = 'benhur41'\r\n"
					+ "ORDER BY num";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				request = new Request();
				request.setNum(rs.getInt("num"));
				request.setNickName(rs.getString("nick_name"));
				request.setRepair(rs.getString("repair"));
				request.setState(rs.getString("state"));
				request.setDiscountPrice(rs.getInt("discount_price"));
				
				list.add(request);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	//본인 신청 조회
	public List<Request> getMyRequstList (String nickName) {
		List<Request> list = new ArrayList<>();
		Request request = null;
		try {
			conn();
			String sql = "SELECT num, nick_name, repair, decode(state , 'R' , '수리중',\r\n"
					+ "                                              'P' , '배송중',\r\n"
					+ "                                                    '수리전') \"state\",decode(customer_grade,'A',price*0.9,\r\n"
					+ "                                                             'B',price*0.95,\r\n"
					+ "                                                             'C',price*0.97,\r\n"
					+ "                                                                 price) \"discount_price\"\r\n"
					+ "FROM fishuser \r\n"
					+ "join request using (nick_name)\r\n"
					+ "join repair using (rp_num)\r\n"
					+ "WHERE nick_name = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				request = new Request();
				request.setNum(rs.getInt("num"));
				request.setNickName(rs.getString("nick_name"));
				request.setRepair(rs.getString("repair"));
				request.setState(rs.getString("state"));
				request.setDiscountPrice(rs.getInt("discount_price"));
				
				list.add(request);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	//request state 상태갱신
	public int updateState(String grade , int num) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE request SET state = ? WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, grade);
			pstmt.setInt(2, num);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	//delete request row + number update
	public int deleteRequest(int num) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM request WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			if(result >0) {
				String sql2 = "UPDATE request SET num = num-1 WHERE num > ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, num);
				
				result = pstmt.executeUpdate();
			}else {
				System.out.println("번호 갱신에 실패했습니다");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	
	//수리요청 순위
	public List<Request> getRanking(){
		List<Request> list = new ArrayList<>();
		Request request = null;
		try {
			conn();
			String sql ="SELECT repair, count(repair) as \"count\"\r\n"
					+ "FROM repair  join request  using (rp_num)\r\n"
					+ "group by repair\r\n"
					+ "ORDER BY count(repair) DESC";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				request = new Request();
				request.setRepair(rs.getString("repair"));
				request.setCount(rs.getInt("count"));
				list.add(request);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	
	
	
	
	
}
