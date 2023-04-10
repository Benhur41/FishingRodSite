package com.home.exe;

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
		while(true) {
			if(FishExe.fishUserInfo != null) {
				if(FishExe.fishUserInfo.getCustomerGrade().equals("M")) {
					manager();
				}else {
					customer();
				}
			}
			else if(FishExe.fishUserInfo == null) {
			int menu = 0;
			System.out.println("낚시병원에 오신 것을 환영합니다.\n");
			while(true) {
				System.out.println("  1. 로그인  |  2. 회원가입  |  3. 종료  ");
				menu = Integer.parseInt(sc.nextLine());
				if(menu == 1) {
					fu.login();	
					break;
				}else if(menu == 2) {
					fu.register();
				}else if(menu == 3) {
					System.out.println("종료 합니다.");
					break;
				}
			}
		}
	}
}
	
	
	private void manager() {
		int menu = 0;
		if(FishExe.fishUserInfo != null) {
		while(true) {
			System.out.println("1. 수리 신청 현황  2. 낚싯대 수리요청 종류 순위  3.사용자 낚싯대 브랜드 순위 4. 회원조회  5. 단일 회원조회  6. 회원삭제  7. 게시판 글 조회 ->게시판 글 삭제  8. 로그아웃");
			System.out.println("번호 입력 >");
			menu = Integer.parseInt(sc.nextLine());
			if (menu == 1) {
				// 수리 테이블 전체 조회 -> 삭제 /갱신
				while(true) {
					rs.getRequestList();
					System.out.println(" 1. 신청글 삭제  |  2. 수리현황 갱신  | 3. 뒤로가기  ");
					int selectNo = Integer.parseInt(sc.nextLine());
					if(selectNo == 1) {
						rs.deleteRequest();
					}else if(selectNo == 2) {
						rs.updateState();
					}else if(selectNo == 3) {
						break;
					}
				}
			}else if(menu == 2) {
				// 수리 테이블 순위 형태로 나타내서 조회
				rs.getRanking();
			}else if(menu == 3) {
				fu.getRodList();
			}else if(menu == 4) {
				//회원 전체 조회
				fu.getUserList();
			}else if(menu == 5) {
				//회원 단일 조회
				fu.getUser();
			}else if(menu == 6) {
				//회원 삭제
				fu.deleteUser();
			}else if(menu == 7) {
				//게시판 글 조회 /삭제
				//삭제기능
				deleteOrNot();
			}else if(menu ==8) {
				//로그아웃
				FishExe.fishUserInfo =null;
				break;
			}
		}
	}else {
		System.out.println("로그아웃 합니다.");
	}
	}
	
	private void customer (){
		int menu = 0 ;
		if(FishExe.fishUserInfo != null) {
		while(true) {
			System.out.println("  1. 마이페이지  |  2. 낚싯대 수리 신청  |  3. 커뮤니티 |  4. 로그아웃");
			System.out.println("번호 입력 >");
			menu = Integer.parseInt(sc.nextLine());
			
			if(menu == 1 ) {
				// 마이 페이지 - fishuser table 활용해서 정보 출력
				myPage();
			}else if(menu == 2) {
				// 낚싯대 수리 신청 글 작성
				rs.writeRq();
			}else if(menu == 3) {
				// 커뮤니티 접속
				commScreen();
			}else if(menu == 4) {
				// 로그아웃
				FishExe.fishUserInfo = null;
				break;
			}
			
		}
	}else {
		System.out.println("로그아웃 합니다.");
	}
	}
	
	
	private void myPage () {
		int menu = 0;
		while(true) {
		fu.getMine();
		System.out.println("  1. 정보 수정  |  2. 뒤로가기");
		menu = Integer.parseInt(sc.nextLine());
			if(menu == 1) {
				//정보수정
				fu.fishUserUpdate();
			}else if(menu == 2) {
				//나가기
				break;
			}
		}
		}
	
	//커뮤니티 화면
	private void commScreen() {
		while(true) {
			cs.getCommList();
			System.out.println("1) 글 조회 2) 글 작성 3) 글 수정 4) 글 삭제 5) 나가기");
			System.out.println("입력 >");
			int selectNo = Integer.parseInt(sc.nextLine());
			if(selectNo == 1) {
				//단일 글 조회 (추천 댓글작성 댓글 삭제)
				deepCommScreen();
			}else if(selectNo ==2) {
				//글작성
				cs.writeText();
			}else if(selectNo ==3) {
				//글 수정 (본인 것만)
				cs.updateComm();
			}else if(selectNo ==4) {
				//글 삭제 (본인 것만)
				cs.deleteComm();
			}else if(selectNo ==5) {
				break;
			}
		}
	}
	
	
	//글조회시 추천 댓작 댓삭 기능
	private void deepCommScreen() {
		while(true) {
			cs.getComm();
			System.out.println("  1. 댓글 추천  |  2. 댓글 작성  |  3. 댓글 삭제  |  4. 나가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			if(selectNo == 1) {
				//추천
				cms.CMRecommand();
			}else if(selectNo == 2) {
				//댓글작성
				cms.writeComment();
			}else if(selectNo == 3) {
				//댓글 삭제
				cms.deleteComment();
			}else if(selectNo == 4) {
				FishExe.communityInfo = null;
				break;
			}
			
		}
	}
	
	private void deleteOrNot() {
		while(true) {
			cs.getCommList();
			cs.getComm();
			System.out.println("  1. 글 조회  |  2. 글 수정  |  3. 글 삭제  |  4. 나가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			if(selectNo == 1) {
				deepCommScreen();
			}else if(selectNo == 2) {
				//글 수정 (본인 것만)
				cs.updateComm();
			}else if(selectNo == 3) {
				//글 삭제 (본인 것만)
				cs.deleteComm();
			}else if(selectNo == 4) {
				FishExe.communityInfo = null;
				break;
			}
			
		}
	}
}

