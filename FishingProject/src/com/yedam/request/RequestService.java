package com.yedam.request;

import java.util.List;
import java.util.Scanner;

import com.home.exe.FishExe;

public class RequestService {

	Scanner sc = new Scanner(System.in);
	
	//전체 조회
	public void getRequestList() {
		List<Request> list = RequestDAO.getInstance().getRequstList();
		
		for(Request r : list) {
			System.out.printf("no. %3d | 작성자 : %10s | 수리내용 : %5s | 수리현황 : %5s | 가격 : %5d\n" , r.getNum(),r.getNickName(), r.getRepair(), r.getState(), r.getDiscountPrice());
		}
	}
	
	//saverq 조회
	public void getMyFinish() {
		Request rq = RequestDAO.getInstance().getMyFinish();
		System.out.printf("수리명 : %-5s  |  할인가 : %f  |  상태  :  %s \n",rq.getRepair(),rq.getDiscountPrice(),rq.getState());
	}
	
	//본인 신청 조회
	public void getMyRequestList() {
		List<Request> list = RequestDAO.getInstance().getMyRequstList(FishExe.fishUserInfo.getNickName());
		
		for(Request r : list) {
			System.out.println(" 작성자 : "+r.getNickName() + " 수리내용 : " + r.getRepair()+ " 수리현황 : " + r.getState() + " 가격 : " + r.getDiscountPrice());
		}
	}
	
	//관리자의 신청자 수리현황 갱신
	public void updateState() {
		System.out.println("수리현황 갱신할 글 번호를 입력해주세요");
		System.out.println("입력 >");
		int num = Integer.parseInt(sc.nextLine());
		System.out.println("갱신할 상태 입력해주세요 (R: 수리중 P: 배송중 N: 수리 )");
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
	
	//수리 신청글 작성
	public void writeRq() {
		int result = 0;
		Request request = null;
		System.out.println("원하시는 수리를 선택해주세요 >");
		System.out.println(" 1. 세척/점검  |  2. 초리복원  |  3. 탑 교환  |  4. 손잡이대 복원  |  5. 가이드 교환  ");
		
		int selectNo = Integer.parseInt(sc.nextLine());
		if(selectNo == 1) {
			result = write(selectNo);
		}else if(selectNo ==2) {
			result = write(selectNo);
		}else if(selectNo ==3) {
			result = write(selectNo);
		}else if(selectNo ==4) {
			result = write(selectNo);
		}else if(selectNo ==5) {
			result = write(selectNo);
		}
		if(result > 0) {
			System.out.println("신청에 성공하였습니다.");
		}else {
			System.out.println("신청에 실패하였습니다.");
		}
	}
	
	
	//수리 신청글 method1
	private int write(int selectNo) {
		int result =0;
		Request request = new Request();
		request.setRpNum(selectNo);
		request.setNickName(FishExe.fishUserInfo.getNickName());
		request.setState("N");
		result = RequestDAO.getInstance().writeRq(request);
		
		return result;
		
	}
	
	//delete + Insert into saverq
	public void repairCountUp() {
		System.out.println("완료할 신청글의 번호를 선택하세요.");
		System.out.println("입력 >");
		int no = Integer.parseInt(sc.nextLine());
		Request rq = RequestDAO.getInstance().getRequest(no);
		
		int result = RequestDAO.getInstance().deleteRequest(no);
		if(result > 0) {
			result = RequestDAO.getInstance().putSaveRq(rq);
			if(result > 0 ) {
				System.out.println("신청횟수 갱신에 성공했습니다");
			}else {
				System.out.println("신청횟수 갱신에 실패했습니다.");
			}
		}else {
			System.out.println("삭제 실패하였습니다.");
		}
		
	}
	
	
	
	
	
	
}
