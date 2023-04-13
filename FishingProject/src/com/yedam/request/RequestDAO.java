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
			String sql = "SELECT num, nick_name,fishingRod, repair, decode(state , 'R' , '수리중',\r\n"
					+ "                                              'P' , '배송중',\r\n"
					+ "                                                    '수리전') \"state\",decode(customer_grade,'A',price*0.9,\r\n"
					+ "                                                             'B',price*0.95,\r\n"
					+ "                                                             'C',price*0.97,\r\n"
					+ "                                                                 price) \"discount_price\"\r\n"
					+ "FROM fishuser \r\n"
					+ "join request using (nick_name)\r\n"
					+ "join repairs using (rp_num)\r\n"
					+ "ORDER BY num";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				request = new Request();
				request.setNum(rs.getInt("num"));
				request.setNickName(rs.getString("nick_name"));
				request.setFishingRod(rs.getString("fishingRod"));
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
			String sql = "SELECT num, nick_name,fishingRod, repair, decode(state , 'R' , '수리중',\r\n"
					+ "                                              'P' , '배송중',\r\n"
					+ "                                                    '수리전') \"state\",decode(customer_grade,'A',price*0.9,\r\n"
					+ "                                                             'B',price*0.95,\r\n"
					+ "                                                             'C',price*0.97,\r\n"
					+ "                                                                 price) \"discount_price\"\r\n"
					+ "FROM fishuser \r\n"
					+ "join request using (nick_name)\r\n"
					+ "join repairs using (rp_num)\r\n"
					+ "WHERE nick_name = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickName);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				request = new Request();
//				request.setNum(rs.getInt("num"));
				request.setNickName(rs.getString("nick_name"));
				request.setRepair(rs.getString("repair"));
				request.setFishingRod(rs.getString("fishingRod"));
				request.setState(rs.getString("state"));
				request.setDiscountPrice(rs.getInt("discount_price"));
				request.setNum(rs.getInt("num"));
				
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
			String sql = "UPDATE request SET state = UPPER(?) WHERE num = ?";
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
		int result1 = 0;
		try {
			conn();
			String sql = "DELETE FROM request WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result1 = pstmt.executeUpdate();
			if(result1 >0) {
				String sql2 = "UPDATE request SET num = num-1 WHERE num > ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, num);
				
				int result = pstmt.executeUpdate();
			}else {
				System.out.println("번호 갱신에 실패했습니다");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result1;
	}
	
	//수리번호로 돈값 가져오기
	public Request getPrice (int no) {
		Request rq = null;
		try {
			conn();
			String sql = "SELECT num, nick_name,fishingRod,rp_num, repair, decode(state , 'R' , '수리중',\r\n"
					+ "                                                        'P' , '배송중',\r\n"
					+ "                                                             '수리전') \"state\" ,decode(customer_grade,'A',price*0.9,\r\n"
					+ "					                                                             'B',price*0.95,\r\n"
					+ "					                                                            'C',price*0.97,\r\n"
					+ "					                                                                 price) \"discount_price\"\r\n"
					+ "FROM fishuser \r\n"
					+ "join request using (nick_name)\r\n"
					+ "join repairs using (rp_num)\r\n"
					+ "WHERE num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rq = new Request();
				rq.setDiscountPrice(rs.getDouble("discount_price"));
				rq.setRpNum(rs.getInt("rp_num"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return rq;
	}
	
	
	//repair 에 매출 더하기
	public int setPrice(Request r) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE repairs SET sales = sales + ? WHERE rp_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, r.getDiscountPrice());
			pstmt.setInt(2, r.getRpNum());
			result = pstmt.executeUpdate();
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	//매출
	public List<Request> getSales() {
		List<Request> list = new ArrayList<>();
		Request rq = null;
		try {
			conn();
			String sql = "SELECT repair , count , sales\r\n"
					+ "FROM repairs join (SELECT rp_num , count(rp_num) count FROM saverq GROUP BY rp_num) USING (rp_num)";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				rq = new Request();
				rq.setCount(rs.getInt("count"));
				rq.setSales(rs.getDouble("sales"));
				rq.setRepair(rs.getString("repair"));
				list.add(rq);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return list;
	}
	//saveRq 에 넣기
	public int putSaveRq(Request r) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO saverq VALUES (?,?,?,'완')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getNickName());
			pstmt.setString(2, r.getFishingRod());
			pstmt.setInt(3, r.getRpNum());
//			pstmt.setDouble(4, r.getDiscountPrice());
			
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
	//saverq 에서 순위세기
	public List<Request> getSaveRank(){
		List<Request> list = new ArrayList<>();
		Request request = null;
		try {
			conn();
			String sql = "SELECT repair, count(repair) as \"count\"\r\n"
					+ "FROM repairs  \r\n"
					+ "join saverq using (rp_num)\r\n"
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
			String sql = "INSERT INTO request VALUES (NVL((SELECT max(num)FROM request)+1,1),?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getNickName());
			pstmt.setString(2, r.getFishingRod());
			pstmt.setInt(3, r.getRpNum());
			pstmt.setString(4, r.getState());
			
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
				rq.setFishingRod(rs.getString("fishingRod"));
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return rq;
	}
	
	//본인의 완료된 request 조회 
	public List<Request> getMyFinishList() {
		List<Request> list = new ArrayList<>();
		Request rq = null;
		try {
			conn();
			String sql = "SELECT nick_name ,fishingRod, decode(rp_num,1,'세척/점검',\r\n"
					+ "                                2,'초리복원',\r\n"
					+ "                                3,'탑교환',\r\n"
					+ "                                4,'손잡이대복원',\r\n"
					+ "                                5,'가이드교환') \"repair\" ,state\r\n"
					+ "FROM saverq\r\n"
					+ "WHERE nick_name = ?";
			
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, FishExe.fishUserInfo.getNickName());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				rq = new Request();
				rq.setRepair(rs.getString("repair"));
				rq.setFishingRod(rs.getString("fishingRod"));
				rq.setNickName(rs.getString("nick_name"));
				rq.setState(rs.getString("state"));
				list.add(rq);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return list;
	}
	
	
}
