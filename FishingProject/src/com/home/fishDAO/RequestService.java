package com.home.fishDAO;

import java.util.List;
import java.util.Scanner;

import com.home.exe.FishExe;

public class RequestService {

	Scanner sc = new Scanner(System.in);
	
	//전체 조회
	public void getRequestList() {
		List<Request> list = RequestDAO.getInstance().getRequstList();
		
		for(Request r : list) {
			System.out.println("no." + r.getNum()+" | 작성자 : "+r.getNickName() + " | 수리내용 : " + r.getRepair()+ " | 수리현황 : " + r.getState() + " | 가격 : " + r.getDiscountPrice());
		}
	}
	
	//본인 신청 조회
	public void getMyRequestList() {
		List<Request> list = RequestDAO.getInstance().getMyRequstList(FishExe.fishUserInfo.getNickName());
		
		for(Request r : list) {
			System.out.println("no." + r.getNum()+" 작성자 : "+r.getNickName() + " 수리내용 : " + r.getRepair()+ " 수리현황 : " + r.getState() + " 가격 : " + r.getDiscountPrice());
		}
	}
	
	//관리자의 신청자 수리현황 갱신
	public void updateState() {
		System.out.println("수리현황 갱신할 글 번호를 입력해주세요");
		System.out.println("입력 >");
		int num = Integer.parseInt(sc.nextLine());
		System.out.println("갱신할 상태 입력해주세요 (R: 수리중 P: 배송중 N: 수리전)");
		System.out.println("입력 >");
		String state = sc.nextLine();
		
		int result = RequestDAO.getInstance().updateState(state, num);
		
		if(result > 0) {
			System.out.println("갱신에 성공했습니다.");
		}else {
			System.out.println("갱신에 실패했습니다");
		}
	}
	
	//신청글 삭제
	public void deleteRequest() {
		System.out.println("삭제할 신청의 글 번호를 입력해주세요.");
		int num = Integer.parseInt(sc.nextLine());
		
		int result = RequestDAO.getInstance().deleteRequest(num);
		
		if(result > 0 ) {
			System.out.println("삭제 성공했습니다.");
		}else {
			System.out.println("삭제 실패했습니다.");
		}
	}
	
	//수리 요청 순위
	public void getRanking() {
		List<Request> list = RequestDAO.getInstance().getRanking();
		int i = 1;
		for(Request r : list) {
			
			System.out.println(i++ +" 순위 : " + r.getRepair() + " 신청 건수 : " + r.getCount() );
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
