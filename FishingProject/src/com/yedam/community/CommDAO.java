package com.yedam.community;

import java.util.ArrayList;
import java.util.List;

import com.home.common.DAO;
import com.home.exe.FishExe;

public class CommDAO extends DAO {
	
	private static CommDAO commDao = new CommDAO();
	
	private CommDAO () {}
	
	public static CommDAO getInstance() {
		return commDao;
	}
	
	//글 전체 조회
	public List<Community> getCommList(){
		List<Community> list = new ArrayList<>();
		Community comm = null;
		try {
			conn();
			String sql = "SELECT * FROM community ORDER BY co_num DESC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				comm = new Community();
				comm.setCoNum(rs.getInt("co_num"));
				comm.setNickName(rs.getString("nick_name"));
				comm.setTitle(rs.getString("title"));
				comm.setContent(rs.getString("content"));
				comm.setWriteDate(rs.getDate("write_date"));
				comm.setViews(rs.getInt("views"));
				comm.setRecommand(rs.getInt("recommand"));
				comm.setNonRecommand(rs.getInt("non_recommand"));
				list.add(comm);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	//단일 글 조회
	public Community getComm(int no) {
		Community comm = null;
		try {
			conn();
			String sql = "SELECT co_num , title , nick_name , content , write_date , recommand,non_recommand FROM community WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				comm = new Community();
				comm.setCoNum(rs.getInt("co_num"));
				comm.setTitle(rs.getString("title"));
				comm.setNickName(rs.getString("nick_name"));
				comm.setContent(rs.getString("content"));
				comm.setWriteDate(rs.getDate("write_date"));
				comm.setRecommand(rs.getInt("recommand"));
				comm.setNonRecommand(rs.getInt("non_recommand"));
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return comm;
	}
	
	
	// 글작성 기능
	public int writeText(Community comm) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO community VALUES (NVL((SELECT max(co_num) FROM community)+1,1),?,?,?,sysdate,0,0,0)";
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, comm.getCoNum());
			pstmt.setString(1, comm.getNickName());
			pstmt.setString(2, comm.getTitle());
			pstmt.setString(3, comm.getContent());
//			pstmt.setDate(5, comm.getWriteDate());
//			pstmt.setInt(4, comm.getViews());
//			pstmt.setInt(5, comm.getRecommand());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	
	//글 수정 본인만
	public int updateComm(String reWrite , int no,int coNum) {
		int result =0;
		try {
			conn();
			if(no == 1) {
			String sql = "UPDATE community SET title = ? WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reWrite);
			}else if(no == 2) {
				String sql = "UPDATE community SET content =? WHERE co_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, reWrite);
			}
			pstmt.setInt(2, coNum);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	
	//글삭제 본인것만
	public int deleteComm(int coNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM community WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coNum);
			
			result = pstmt.executeUpdate();
			if(result > 0) {
				String sql2 ="UPDATE community SET co_num = co_num -1 WHERE co_num > ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, coNum);
				int result2 = pstmt.executeUpdate();
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	
	//글 추천 기능
	public int recommandComm() {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE community SET recommand = recommand + 1 WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FishExe.communityInfo.getCoNum());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	
	//비추 기능
	public int commThumbsDown() {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE community SET non_recommand = non_recommand +1 WHERE co_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FishExe.communityInfo.getCoNum());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
		
	}
	// 조회수 추가 기능
	public int updateView(int no) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE community SET views = views +1 WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
}
