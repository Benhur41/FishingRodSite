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
			String sql = "SELECT num , nick_name , content , write_date , recommand,non_recommand FROM comments WHERE co_num = ? ORDER BY num";
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
				cm.setNonRecommand(rs.getInt("non_recommand"));
				list.add(cm);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return list;
	}
	//num 으로 truenum 불러오기
	public Comments getTN(int no) {
		Comments cms = null;
		try {
			conn();
			String sql = "SELECT true_num FROM comments WHERE num =? AND co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setInt(2, FishExe.communityInfo.getCoNum());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cms = new Comments();
				cms.setTrueNum(rs.getInt("true_num"));
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return cms;
	}
	//추천 기능
	public int CMRecommand(int num) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE comments SET recommand = recommand+1 WHERE num =? AND co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, FishExe.communityInfo.getCoNum());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
		
	}
	//댓글 비추 기능
	public int CMNonRecommand(int num) {
		int result = 0;
		try {
			conn();
			String sql = "UPDATE comments SET non_recommand = non_recommand+1 WHERE num = ? AND co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, FishExe.communityInfo.getCoNum());
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
			String sql = "INSERT INTO comments VALUES ( NVL((SELECT max(true_num) FROM comments)+1,1),?,NVL((SELECT max(num) FROM comments WHERE co_num =?) +1,1) ,? ,? ,sysdate,0,0)";
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
			String sql = "DELETE FROM comments WHERE num = ? AND co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, FishExe.communityInfo.getCoNum());
			
			result = pstmt.executeUpdate();
			if(result >0) {
				String sql2 = "UPDATE comments SET num = num - 1 WHERE co_num =? AND num > ?";
				pstmt= conn.prepareStatement(sql2);
				pstmt.setInt(1, FishExe.communityInfo.getCoNum());
				pstmt.setInt(2, num);
				
				int result2 = pstmt.executeUpdate();
			}
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
			String sql = "SELECT nick_name,true_num FROM comments WHERE num =? AND co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, FishExe.communityInfo.getCoNum());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cm = new Comments();
				cm.setNickName(rs.getString("nick_name"));
				cm.setTrueNum(rs.getInt("true_num"));
			}
					
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return cm;
	}
	
	//글 추천테이블 리스트 조회
	public List<Comments> duplication(int coNum){
		List<Comments> list = new ArrayList<>();
		Comments cm = null;
		try {
			conn();
			String sql = "SELECT * FROM recommand_safe WHERE co_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cm = new Comments();
				cm.setCoNum(rs.getInt("co_num"));
				cm.setNickName(rs.getString("nick_name"));
				list.add(cm);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return list;
	}
	//글 비추천테이블 리스트 조회
	public List<Comments> nonDuplication(int coNum){
		List<Comments> list = new ArrayList<>();
		Comments cms = null;
		try {
			conn();
			String sql = "SELECT * FROM non_recommand_safe WHERE co_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cms = new Comments();
				cms.setCoNum(rs.getInt("co_num"));
				cms.setNickName(rs.getString("nick_name"));
				list.add(cms);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return list;
	}
	//댓글 추천 테이블리스트조회
	public List<Comments> CMduplication(int tn){
		List<Comments> list = new ArrayList<>();
		Comments cm = null;
		try {
			conn();
			String sql = "SELECT * FROM recommand_safe_CMS WHERE true_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tn);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cm = new Comments();
				cm.setTrueNum(rs.getInt("true_num"));
				cm.setNickName(rs.getString("nick_name"));
				list.add(cm);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return list;
	}
	//댓글 비추천 테이블 리스트 조회
	public List<Comments> CMNonDuplication(int tn){
		List<Comments> list = new ArrayList<>();
		Comments cms = null;
		try {
			conn();
			String sql ="SELECT * FROM non_recommand_safe_CMS WHERE true_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tn);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cms = new Comments();
				cms.setTrueNum(rs.getInt("true_num"));
				cms.setNickName(rs.getString("nick_name"));
				list.add(cms);
			}
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return list;
	}
	
	//추천 테이블에 삽입기능
	public int putRecoSafe() {
		int result = 0;
		try {
			conn();
			String sql = " INSERT INTO recommand_safe VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FishExe.communityInfo.getCoNum());
			pstmt.setString(2, FishExe.fishUserInfo.getNickName());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	//글 비추천 테이블 삽입 기능
	public int putNonRecoSafe() {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO non_recommand_safe VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, FishExe.communityInfo.getCoNum());
			pstmt.setString(2, FishExe.fishUserInfo.getNickName());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	//댓글 추천위한 삽입기능
	public int putRecoSafeCM(int tn) {
		int result = 0;
		try {
			conn();
			String sql = " INSERT INTO recommand_safe_CMS VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tn);
			pstmt.setString(2, FishExe.fishUserInfo.getNickName());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
	//댓글 비추천 위한 삽입 기능
	public int putNonRecoSafeCM(int tn) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO non_recommand_safe_CMS VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tn);
			pstmt.setString(2, FishExe.fishUserInfo.getNickName());
			result = pstmt.executeUpdate();
		}catch(Exception e) {
		e.printStackTrace();
		}finally {
		disconn();
		}
		return result;
	}
}
