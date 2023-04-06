package com.home.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FishApplication {
	//김태연 0407 00:56 
	// 회원가입 , 로그인 기능 작성 - 작동확인 (완)
	Scanner sc = new Scanner(System.in);
	// 현재 로그인 중 인지 확인하는 static 필드
	public static FishUser fishUserInfo = null;
	
	
	//회원가입 기능
	public void register() {
		System.out.println("낚시병원 사이트에 오신 것을 환영합니다!");
		String[] rodList = new String[5];//낚싯대 이름 저장하는 리스트
		String id = "";
		while(true) {
			System.out.println("사용하실 ID 를 입력해주세요. >");
			id = sc.nextLine();
			FishUser fu = FishDAO.getInstance().login(id);
			if(fu == null) {
				System.out.println("아이디를 사용할 수 있습니다!");
				break;
			}else {
				System.out.println("이미 사용중인 아이디 입니다.");
			}
		}
			System.out.println("사용하실 password 를 입력해주세요. >");
			String pw = sc.nextLine();
			System.out.println("성함을 입력해주세요. >");
			String name = sc.nextLine();
			System.out.println("주소를 입력해주세요. >");
			String address = sc.nextLine();
			System.out.println("전화번호를 입력해주세요 (- 포함) >");
			String phoneNum = sc.nextLine();
			System.out.println("등록하실 낚싯대의 수를 입력해주세요. (최대 5개) >");
			int rodNum = Integer.parseInt(sc.nextLine());
			for(int i = 0 ; i <rodNum ; i++) {
				System.out.println("등록하실 낚싯대 제품명을 입력해주세요 >");
				String rod = sc.nextLine();
				rodList[i] = rod;
			}
			FishUser fu = new FishUser();
			
			fu.setId(id);
			fu.setPw(pw);
			fu.setName(name);
			fu.setAddress(address);
			fu.setPhoneNum(phoneNum);
			fu.setFishingRod1(rodList[0]);
			fu.setFishingRod2(rodList[1]);
			fu.setFishingRod3(rodList[2]);
			fu.setFishingRod4(rodList[3]);
			fu.setFishingRod5(rodList[4]);
			
			int result = FishDAO.getInstance().register(fu);
			
			if(result > 0 ) {
				System.out.println("회원가입이 완료되었습니다!");
			}else {
				System.out.println("회원가입에 실패하였습니다.");
			}
			}
			
		
	
	
	
	//로그인
	public void login() {
		while(true) {
		FishUser fishUser = null;
		System.out.println("아이디를 입력해주세요 >");
		String id = sc.nextLine();
		System.out.println("비밀번호를 입력해주세요 >");
		String pw = sc.nextLine();
		
		fishUser = FishDAO.getInstance().login(id);
		
		if(fishUser != null) {
			if(fishUser.getPw().equals(pw)) {
			System.out.println("정상적으로 로그인 되었습니다.");
			System.out.println(fishUser.getName() + "님 환영합니다 😁😁");
			fishUserInfo = fishUser;
			break;
			}else {
				System.out.println("비밀번호를 틀리셨습니다.");
			}
		}else {
			System.out.println("아이디가 존재하지 않습니다.");
		}
		
	}
	}
}
