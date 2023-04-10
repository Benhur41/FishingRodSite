package com.yedam.request;

import java.util.ArrayList;
import java.util.List;

import com.home.common.DAO;
import com.home.exe.FishExe;

public class RequestDAO extends DAO {
//		try {
//		conn();
//	}catch(Exception e) {
//	e.printStackTrace();
//	}finally {
//	disconn();
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
//				request.setNum(rs.getInt("num"));
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
	
	//saveRq 에 넣기
	public int putSaveRq(Request r) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO saverq VALUES (?,?,'완')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getNickName());
			pstmt.setInt(2, r.getRpNum());
			
			result = pstmt.executeUpdate();
			if(result > 0) {
				String sql2 = "UPDATE fishuser SET repair_count = repair_count +1 WHERE nick_name = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, r.getNickName());
				
				result = pstmt.executeUpdate();
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
	
	//본인 신청글 조회
	
	//신청글 작성
	public int writeRq (Request r) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO request VALUES ((SELECT max(num)FROM request)+1,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getNickName());
			pstmt.setInt(2, r.getRpNum());
			pstmt.setString(3, r.getState());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	//request 단일 조회
	public Request getRequest(int no) {
		Request rq = null;
		try {
			conn();
			String sql ="SELECT * FROM request WHERE num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rq = new Request();
				rq.setNickName(rs.getString("nick_name"));
				rq.setRpNum(rs.getInt("rp_num"));
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return rq;
	}
	
	//본인의 완료된 request 조회 
	public Request getMyFinish() {
		Request rq = null;
		try {
			conn();
			String sql = "SELECT repair , decode( customer_grade , 'A' ,price*0.9,\r\n"
					+ "                                        'B' , price*0.95,\r\n"
					+ "                                        'C' , price*0.98) as \"discount\",state\r\n"
					+ "FROM repair \r\n"
					+ "join request USING (rp_num)\r\n"
					+ "join fishuser USING (nick_name)\r\n"
					+ "WHERE nick_name = ?";
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, FishExe.fishUserInfo.getNickName());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rq = new Request();
				rq.setRepair(rs.getString("repair"));
				rq.setDiscountPrice(rs.getDouble("discount"));
				rq.setState(rs.getString("state"));
				
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return rq;
	}
	
	
}
