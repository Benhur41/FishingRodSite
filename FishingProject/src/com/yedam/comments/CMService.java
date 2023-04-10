package com.yedam.comments;

import java.util.List;
import java.util.Scanner;

import com.home.exe.FishExe;
import com.yedam.community.Community;

public class CMService {
	Scanner sc = new Scanner(System.in);
	
	// 해당 글의 댓글 전체 조회 기능
	public void getCmList(int no) {
		List<Comments> list = CommentsDAO.getInstance().getCmList(no);
		
		for(Comments c : list) {
			System.out.printf("%3d)  %-30s 		  작성자 : %-10s  작성일 : %s 추천수 : %-3d\n",c.getNum() , c.getContent() , c.getNickName(), c.getWriteDate() , c.getRecommand()	);
		}
	}
	
	//댓글추천
	public void CMRecommand() {
		System.out.println("추천할 댓글 번호를 입력해주세요.");
		System.out.println("입력 >");
		int num = Integer.parseInt(sc.nextLine());
		
		int result = CommentsDAO.getInstance().CMRecommand(num);
		if(result > 0) {
			System.out.println("추천에 성공했습니다.");
		}else {
			System.out.println("추천에 실패했습니다.");
		}
	}
	
	//댓글 작성
	public void writeComment() {
		System.out.println("작성할 내용을 입력해주세요");
		System.out.println("입력 >");
		String input = sc.nextLine();
		int result = CommentsDAO.getInstance().writeComment(input);
		
		if(result >0) {
			System.out.println("작성에 성공했습니다.");
		}else {
			System.out.println("작성에 실패했습니다.");
		}
	}
	
	//댓글 삭제 본인만
	public void deleteComment() {
		System.out.println("삭제할 댓글의 번호를 입력하세요.");
		System.out.println("입력 >");
		int num = Integer.parseInt(sc.nextLine());
		Comments cm = CommentsDAO.getInstance().getCMInfo(num);
		if(cm.getNickName().equals(FishExe.fishUserInfo.getNickName()) || FishExe.fishUserInfo.getId().equals("kty12")) {
			int result = CommentsDAO.getInstance().deleteComments(num);
			if(result > 0) {
				System.out.println("삭제 성공했습니다.");
			}else {
				System.out.println("삭제 실패했습니다.");
			}
		}else {
			System.out.println("본인이 작성한 댓글만 지울수있습니다.");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
