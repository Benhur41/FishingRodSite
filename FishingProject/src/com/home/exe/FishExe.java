package com.home.exe;

import java.util.Scanner;

import com.home.fishDAO.FishService;
import com.home.fishDAO.FishUser;
import com.home.fishDAO.RequestService;

public class FishExe {
	Scanner sc = new Scanner(System.in);
	FishService fu = new FishService();
	RequestService rs = new RequestService();
	public static FishUser fishUserInfo = null;
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
			}else if(menu == 6) {
				//회원 삭제
			}else if(menu == 7) {
				//게시판 글 조회 /삭제
			}else if(menu ==8) {
				//로그아웃
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
			System.out.println("  1. 마이페이지  |  2. 낚싯대 수리 신청( 수리 종류 고르기 : ㄱ.세척 ㄴ.릴 ㄷ.초릿대 ... 등급에 따라 수리가격할인적용)  |  3. 커뮤니티 |  4. 로그아웃");
			System.out.println("번호 입력 >");
			menu = Integer.parseInt(sc.nextLine());
			
			if(menu == 1 ) {
				// 마이 페이지 - fishuser table 활용해서 정보 출력
			}else if(menu == 2) {
				// 낚싯대 수리 신청 글 작성
			}else if(menu == 3) {
				// 커뮤니티 접속
			}else if(menu == 4) {
				// 로그아웃
				break;
			}
			
		}
	}else {
		System.out.println("로그아웃 합니다.");
	}
	}
}
