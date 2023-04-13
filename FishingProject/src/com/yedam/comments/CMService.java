package com.yedam.comments;

import java.util.List;
import java.util.Scanner;

import com.home.exe.FishExe;
import com.yedam.community.CommDAO;
import com.yedam.community.Community;

public class CMService {
	Scanner sc = new Scanner(System.in);
	
	// 해당 글의 댓글 전체 조회 기능
	public void getCmList(int no) {
		List<Comments> list = CommentsDAO.getInstance().getCmList(no);
		
		for(Comments c : list) {
			System.out.printf("%3d)  %-30s 		  작성자 : %-10s  작성일 : %s 추천수 : %-3d   비추천수 : %-3d\n",c.getNum() , c.getContent() , c.getNickName(), c.getWriteDate() , c.getRecommand() ,c.getNonRecommand()	);
		}
	}
	
//	public void recommandComm() {
//		int isThere = 0;
//		List<Comments> list = CommentsDAO.getInstance().duplication(FishExe.communityInfo.getCoNum());
//		for(Comments c : list) {
//			if(c.getNickName().equals(FishExe.fishUserInfo.getNickName())) {
//				isThere++;
//			}
//		}	
//		if(isThere >0) {
//			System.out.println("한 글당 한번만 추천 가능합니다.");
//		}else{
//				int result1 = CommentsDAO.getInstance().putRecoSafe();
//				if(result1 > 0) {
//					
//					int result = CommDAO.getInstance().recommandComm();
//					if(result > 0) {
//						Community cm = CommDAO.getInstance().getComm(FishExe.communityInfo.getCoNum());
//						FishExe.communityInfo =cm;
//						
//						System.out.println("추천이 완료 되었습니다");
//					}else {
//						System.out.println("추천 실패하였습니다.");
//					}
//				}
//			}
//	}
	
	//댓글추천
	public void CMRecommand() {
		int isThere = 0;
		int num = 0;
		System.out.println("추천할 댓글 번호를 입력해주세요.");
		System.out.println("입력 >");
		try {
			num = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			num = 0;
		}
		
		Comments cms = CommentsDAO.getInstance().getCMInfo(num);
		if(cms != null) {
		List<Comments> list = CommentsDAO.getInstance().CMduplication(cms.getTrueNum());
		for(Comments c : list) {
			if(c.getNickName().equals(FishExe.fishUserInfo.getNickName())) {
				isThere++;
			}
		}
		if(isThere > 0) {
			System.out.println("계정당 한번만 추천이 가능합니다.");
		}else {
			int result = CommentsDAO.getInstance().putRecoSafeCM(cms.getTrueNum());
			if(result > 0) {
				
				int result1 = CommentsDAO.getInstance().CMRecommand(num);
				if(result1 > 0) {
					System.out.println("추천에 성공했습니다.");
				}else {
					System.out.println("추천에 실패했습니다.");
				}
			}
		}
		}else {
			System.out.println("추천할수있는 댓글이 없습니다.");
		}
	}
	//댓글 비추천
	public void CMNonRecommand() {
		int isThere = 0;
		int num =0;
		System.out.println("비추천할 댓글 번호를 입력 해주세요.");
		System.out.println("입력 >");
		try {
			num = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			num = 0;
		}
		Comments cms = CommentsDAO.getInstance().getCMInfo(num);
		if(cms != null) {
		List<Comments> list = CommentsDAO.getInstance().CMNonDuplication(cms.getTrueNum());
		for(Comments c : list) {
			if(c.getNickName().equals(FishExe.fishUserInfo.getNickName())) {
				isThere++;
			}
		}
		if(isThere > 0) {
			System.out.println("계정당 한번만 비추천이 가능합니다.");
		}else {
			int result1 = CommentsDAO.getInstance().putNonRecoSafeCM(cms.getTrueNum());
			if(result1 > 0) {
				int result = CommentsDAO.getInstance().CMNonRecommand(num);
				if(result > 0) {
					System.out.println("비추성공 ㅋㅋ");
				}else {
					System.out.println("비추실패");
				}
			}
		}
		}else {
			System.out.println("비추천할수있는 댓글이 없습니다.");
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
		int num = 0;
		System.out.println("삭제할 댓글의 번호를 입력하세요.");
		System.out.println("입력 >");
		try {
			num = Integer.parseInt(sc.nextLine());
		}catch(NumberFormatException e) {
			num = 0;
		}
		Comments cm = CommentsDAO.getInstance().getCMInfo(num);
		if(cm != null) {
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
		}else {
			System.out.println("삭제할수있는 댓글이 없습니다.");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
