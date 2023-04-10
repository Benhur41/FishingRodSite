package com.yedam.community;

import java.util.List;
import java.util.Scanner;

import com.home.exe.FishExe;
import com.yedam.comments.CMService;

public class CommService {
	Scanner sc = new Scanner(System.in);
	CMService cms = new CMService();
	//글 전체 조회
	public void getCommList() {
		List<Community> list = CommDAO.getInstance().getCommList();
		
		for(Community c : list) {
			System.out.println("no." + c.getCoNum() +" | 제목 : " + c.getTitle()+ " | 작성자 : " + c.getNickName()+" | 작성일 : " +c.getWriteDate()+ " | 조회수 : " +c.getViews()+" | 추천수 : "+c.getRecommand());
		}
	}
	
	//단일 글 조회
	public void getComm() {
		Community comm = null;
		int no = 0;
		if(FishExe.communityInfo == null) {
		System.out.println("조회하실 글의 번호를 입력해주세요.");
		System.out.println("입력 >");
		no = Integer.parseInt(sc.nextLine());
		comm = CommDAO.getInstance().getComm(no);
		FishExe.communityInfo = comm;
		
		System.out.printf("no.%d	|	%s				\n",comm.getCoNum() , comm.getTitle());
		System.out.println("작성자 : "+comm.getNickName()+"   작성일 : "+comm.getWriteDate());
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("	  %40s\n",comm.getContent());
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		cms.getCmList(no);
		}else {
			System.out.printf("no.%d	|	%s				\n",FishExe.communityInfo.getCoNum() , FishExe.communityInfo.getTitle());
			System.out.println("작성자 : "+FishExe.communityInfo.getNickName()+"   작성일 : "+FishExe.communityInfo.getWriteDate());
			System.out.println("---------------------------------------------------------------");
			System.out.printf("	  %s\n",FishExe.communityInfo.getContent());
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			cms.getCmList(FishExe.communityInfo.getCoNum());
		}
	}
	
	//글 작성기능
	public void writeText() {
		Community comm = null;
		System.out.println("글 제목을 입력해주세요.");
		System.out.println("입력 >");
		String title = sc.nextLine();
		System.out.println("내용을 입력해주세요 >");
		String content = sc.nextLine();
		
		comm = new Community();
		comm.setNickName(FishExe.fishUserInfo.getNickName());
		comm.setTitle(title);
		comm.setContent(content);
		
		int result = CommDAO.getInstance().writeText(comm);
		if(result > 0) {
			System.out.println("작성 되었습니다.");
		}else {
			System.out.println("작성에 실패했습니다.");
		}
	}
	

	//글수정 본인만
	public void updateComm() {
		System.out.println("수정할 글의 번호를 선택해주세요.");
		System.out.println("입력 >");
		int coNum = Integer.parseInt(sc.nextLine());
		Community comm = CommDAO.getInstance().getComm(coNum);
		if(comm.getNickName().equals(FishExe.fishUserInfo.getNickName()) || FishExe.fishUserInfo.getId().equals("kty12")) {
			System.out.println("변경할 부분을 선택해주세요.");
			System.out.println("  1. 글 제목  | 2. 글 내용");
			System.out.println("입력 >");
			int no = Integer.parseInt(sc.nextLine());
			System.out.println("변경할 내용을 입력해주세요.");
			System.out.println("입력 >");
			String reWrite = sc.nextLine();
			int result = CommDAO.getInstance().updateComm(reWrite, no, coNum);
		}else {
			System.out.println("본인이 작성한 글이 아닙니다.");
		}
		
	}
	
	
	//글삭제 본인것만
	public void deleteComm() {
		System.out.println("삭제할 글의 번호를 입력해주세요.");
		System.out.println("입력 >");
		int coNum = Integer.parseInt(sc.nextLine());
		Community comm = CommDAO.getInstance().getComm(coNum);
		
		if(comm.getNickName().equals(FishExe.fishUserInfo.getNickName()) || FishExe.fishUserInfo.getId().equals("kty12")) {
			int result = CommDAO.getInstance().deleteComm(coNum);
			if(result > 0) {
				System.out.println("삭제에 성공했습니다.");
			}else {
				System.out.println("삭제에 실패했습니다.");
			}
		}else {
			System.out.println("본인이 작성한 글이 아닙니다.");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
