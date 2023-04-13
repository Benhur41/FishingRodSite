package com.home.exe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.home.fishDAO.FishService;
import com.home.fishDAO.FishUser;
import com.yedam.comments.CMService;
import com.yedam.community.CommService;
import com.yedam.community.Community;
import com.yedam.request.RequestService;

public class FishExe {
	Scanner sc = new Scanner(System.in);
	FishService fu = new FishService();
	RequestService rs = new RequestService();
	CommService cs = new CommService();
	CMService cms = new CMService();
	
	public static FishUser fishUserInfo = null;
	public static Community communityInfo = null;
	
	public FishExe() {
		run();
	}

	private void run() {
		boolean run = true;
		while(run) {
			if(FishExe.fishUserInfo != null) {
				if(FishExe.fishUserInfo.getCustomerGrade().equals("M")) {
					manager();
				}else {
					customer();
				}
			}
			else if(FishExe.fishUserInfo == null) {
			System.out.println("낚시병원에 오신 것을 환영합니다.\n");
			while(true) {
				String menu = "";
				System.out.println("  1. 로그인  |  2. 회원가입  |  3. 종료  ");
				menu = sc.nextLine();
				if(menu.equals("1")) {
					fu.login();	
					break;
				}else if(menu.equals("2")) {
					fu.register();
				}else if(menu.equals("3")) {
					System.out.println("종료 합니다.");
					run = false;
					break;
				}else {
					System.out.println("정확한 번호를 입력하세요~!");
				}
			}
		}
	}
}
	
	
	private void manager() {
		String menu = "";
		if(FishExe.fishUserInfo != null) {
		while(true) {
			System.out.println("1. 수리 신청 현황  2. 수리요청순위 및 매출  3.사용자 낚싯대 브랜드 순위 4. 회원조회  5. 단일 회원조회  6. 회원삭제  7. 게시판 관리  8. 로그아웃");
			System.out.println("번호 입력 >");
			menu = sc.nextLine();
			if (menu.equals("1")) {
				// 수리 테이블 전체 조회 -> 삭제 /갱신
				while(true) {
					rs.getRequestList();
					System.out.println(" 1. 신청글 삭제  |  2. 수리현황 갱신  | 3. 수리 완료  | 4. 나가기  ");
					String selectNo = sc.nextLine();
					if(selectNo.equals("1")) {
						rs.deleteRequest();
					}else if(selectNo.equals("2")) {
						rs.updateState();
					}else if(selectNo.equals("3")) {
						//수리완료시 삭제하고 saverq 로 넘어가게 하기
						rs.repairCountUp();
					}else if(selectNo.equals("4")) {
						break;
					}else {
						System.out.println("정확한 번호를 입력해주세요~!");
					}
				}
			}else if(menu.equals("2")) {
				// 수리 테이블 순위 형태로 나타내서 조회
				rs.getRanking();
			}else if(menu.equals("3")) {
				fu.getRodList();
			}else if(menu.equals("4")) {
				//회원 전체 조회
				fu.getUserList();
			}else if(menu.equals("5")) {
				//회원 단일 조회
				fu.getUser();
			}else if(menu.equals("6")) {
				//회원 삭제
				fu.deleteUser();
			}else if(menu.equals("7")) {
				//게시판 글 조회 /삭제
				//삭제기능
				commScreen();
			}else if(menu.equals("8")) {
				//로그아웃
				FishExe.fishUserInfo =null;
				break;
			}else {
				System.out.println("정확한 메뉴를 입력해주세요~!");
			}
		}
	}else {
		System.out.println("로그아웃 합니다.");
	}
	}
	
	private void customer (){
		String menu = "";
		if(FishExe.fishUserInfo != null) {
		while(true) {
			System.out.println("  1. 마이페이지  |  2. 낚싯대 수리 신청  |  3. 커뮤니티 |  4. 로그아웃");
			System.out.println("번호 입력 >");
			menu = sc.nextLine();
			
			if(menu.equals("1") ) {
				// 마이 페이지 - fishuser table 활용해서 정보 출력
				myPage();
			}else if(menu.equals("2")) {
				// 낚싯대 수리 신청 글 작성
				rs.writeRq();
			}else if(menu.equals("3")) {
				// 커뮤니티 접속
				commScreen();
			}else if(menu.equals("4")) {
				// 로그아웃
				FishExe.fishUserInfo = null;
				break;
			}else {
				System.out.println("정확한 메뉴를 입력해주세요~!");
			}
			
		}
	}else {
		System.out.println("로그아웃 합니다.");
	}
	}
	
	
	private void myPage () {
		String menu = "";
		while(true) {
		fu.getMine();
		System.out.println("  1. 정보 수정  |  2. 수리신청 취소  | 3. 뒤로가기");
		menu = sc.nextLine();
			if(menu.equals("1")) {
				//정보수정
				fu.fishUserUpdate();
			}else if(menu.equals("2")) {
				//수리 신청 취소
				rs.deleteRequest();
			}else if(menu.equals("3")) {
				//나가기
				break;
			}else {
				System.out.println("정확한 메뉴를 입력해주세요~!");
			}
		}
		}
	
	//커뮤니티 화면
	private void commScreen() {
		while(true) {
			cs.getCommList();
			System.out.println("1) 글 조회 2) 글 작성 3) 글 수정 4) 글 삭제 5) 나가기");
			System.out.println("입력 >");
			String selectNo = sc.nextLine();
			if(selectNo.equals("1")) {
				//단일 글 조회 (추천 댓글작성 댓글 삭제)
				deepCommScreen();
			}else if(selectNo.equals("2")) {
				//글작성
				cs.writeText();
			}else if(selectNo.equals("3")) {
				//글 수정 (본인 것만)
				cs.updateComm();
			}else if(selectNo.equals("4")) {
				//글 삭제 (본인 것만)
				cs.deleteComm();
			}else if(selectNo.equals("5")) {
				break;
			}else {
				System.out.println("정확한 번호를 입력해주세요~!");
			}
		}
	}
	
	
	//글조회시 추천 댓작 댓삭 기능
	private void deepCommScreen() {
		while(true) {
			cs.getComm();
			System.out.println("  1. 글 추천  |  2. 글 비추천  |  3. 댓글 작성   |  4. 댓글 추천  |  5. 댓글 비추천  |  6. 댓글 삭제  |  99. 나가기");
			String selectNo = sc.nextLine();
			if(selectNo.equals("1")){
				cs.recommandComm();
			}else if(selectNo.equals("2")) {
				//글비추천
				cs.commThumbsDown();
			}else if(selectNo.equals("3")) {
				//댓글추천
				cms.writeComment();
			}else if(selectNo.equals("4")) {
				//댓글비추천
				cms.CMRecommand();
			}else if(selectNo.equals("5")) {
				//댓글작성
				cms.CMNonRecommand();
			}else if(selectNo.equals("6")) {
				//댓글 삭제
				cms.deleteComment();
			}else if(selectNo.equals("99")) {
				FishExe.communityInfo = null;
				break;
			}else {
				System.out.println("정확한 번호를 입력해주세요~!!");
			}
			
		}
	}
	//관리자 글 조회 후 삭제 기능
	private void deleteOrNot() {
		while(true) {
			cs.getCommList();
			cs.getComm();
			System.out.println("  1. 글 조회  |  2. 글 수정  |  3. 글 삭제  |  4. 나가기");
			String selectNo = sc.nextLine();
			if(selectNo.equals("1")) {
				deepCommScreen();
			}else if(selectNo.equals("2")) {
				//글 수정 (본인 것만)
				cs.updateComm();
			}else if(selectNo.equals("3")) {
				//글 삭제 (본인 것만)
				cs.deleteComm();
			}else if(selectNo.equals("4")) {
				FishExe.communityInfo = null;
				break;
			}
			
		}
	}
}

