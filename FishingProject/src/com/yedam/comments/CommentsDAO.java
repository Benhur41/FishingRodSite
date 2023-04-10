package com.yedam.comments;

import java.util.ArrayList;
import java.util.List;

import com.home.common.DAO;
import com.home.exe.FishExe;

public class CommentsDAO extends DAO {
	
	private static CommentsDAO commentsDao = new CommentsDAO();
	
	private CommentsDAO() {
		
	}
	
	public static CommentsDAO getInstance() {
		return commentsDao;
	}
	
	//선택된 글의 댓글 조회
	public List<Comments> getCmList(int no){
		List<Comments> list = new ArrayList<>();
		Comments cm = null;
		try {
			conn();
			String sql = "SELECT num , nick_name , content , write_date , recommand FROM comments WHERE co_num = ? ORDER BY num";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cm = new Comments();
				cm.setNum(rs.getInt("num"));
				cm.setNickName(rs.getString("nick_name"));
				cm.setContent(rs.getString("content"));
				cm.setWriteDate(rs.getDate("write_date"));
				cm.setRecommand(rs.getInt("recommand"));
				list.add(cm);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	
	
	//추천 기능
	public int CMRecommand(int num) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE comments SET recommand = recommand+1 WHERE num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
		
	}
	//댓글 작성 기능
	public int writeComment(String input) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO comments VALUES ( ?,NVL((SELECT max(num) FROM comments WHERE co_num =?) +1,1) ,? ,? ,sysdate,0)";
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, FishExe.communityInfo.getCoNum());
			pstmt.setInt(2, FishExe.communityInfo.getCoNum());
			pstmt.setString(3, FishExe.fishUserInfo.getNickName());
			pstmt.setString(4, input);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	
	//댓글 삭제
	public int deleteComments(int num) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM comments WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	
	//번호로 댓글 내용 조회
	public Comments getCMInfo(int num) {
		Comments cm = null;
		try {
			conn();
			String sql = "SELECT nick_name FROM comments WHERE num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cm = new Comments();
				cm.setNickName(rs.getString("nick_name"));
			}
					
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return cm;
	}
}
