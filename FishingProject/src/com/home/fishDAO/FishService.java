package com.home.fishDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.home.exe.FishExe;

public class FishService {
	//김태연 0407 00:56 
	// 회원가입 , 로그인 기능 작성 - 작동확인 (완)
	Scanner sc = new Scanner(System.in);
	// 현재 로그인 중 인지 확인하는 static 필드
//	public static FishUser fishUserInfo = null;
	
	
	//회원가입 기능
	public void register() {
		System.out.println("낚시병원 사이트에 오신 것을 환영합니다!");
		String[] rodList = {"없음","없음","없음","없음","없음"};//낚싯대 이름 저장하는 리스트
		String id = "";
		String nickName = "";
		
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
		
		while(true) {
			System.out.println("사용하실 nick name 을 입력해주세요. >");
			nickName = sc.nextLine();
			FishUser fu = FishDAO.getInstance().checkNick(nickName);
			if(fu == null) {
				System.out.println("닉네임을 사용할 수 있습니다!");
				break;
			}else {
				System.out.println("이미 사용중인 닉네임 입니다.");
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
				System.out.println(i+1 +"번째로 등록하실 낚싯대 제품브랜드를 선택해주세요 >");
				System.out.println("1. 다이와  |  2.  시마노  |  3.  은성  |  4.  바낙스  | 5. ns  ");
				int num = Integer.parseInt(sc.nextLine());
				if(num == 1) {
					rodList[i] = "daiwa:";
				}else if (num == 2) {
					rodList[i] = "shimano:";
				}else if (num == 3) {
					rodList[i] = "eunsung:";
				}else if (num == 4) {
					rodList[i] = "banax:";
				}else if (num == 5) {
					rodList[i] = "ns:";
				}
				System.out.println("제품명을 입력해주세요");
				String rodName = sc.nextLine();
				rodList[i] += rodName;
			}
			FishUser fu = new FishUser();
			
			fu.setId(id);
			fu.setPw(pw);
			fu.setName(name);
			fu.setNick_name(nickName);
			fu.setAddress(address);
			fu.setCustomerPhone(phoneNum);
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
			FishExe.fishUserInfo = fishUser;
			break;
			}else {
				System.out.println("비밀번호를 틀리셨습니다.");
			}
		}else {
			System.out.println("아이디가 존재하지 않습니다.");
		}
		
	}
	}
	
	
	//게시판 글 삭제 기능 ( 댓글 테이블에 관계된 댓글도 같이 삭제된다.)
	public void deleteTwo() {
		System.out.println("삭제할 글 번호를 입력해주세요 >");
		int coNum = Integer.parseInt(sc.nextLine());
		
		int result = FishDAO.getInstance().deleteTwo(coNum);
		
		if(result > 0 ) {
			System.out.println("글 삭제에 성공 하였습니다.");
		}else {
			System.out.println("글 삭제에 실패 하였습니다.");
		}
	}
	
	
	//낚싯대 브랜드 순위
	public void getRodList() {
		List<FishUser> list = FishDAO.getInstance().getRodList();
		Rod[] rodArr = new Rod[5];
		for(int i = 0 ; i < rodArr.length ; i++) {
			rodArr[i]=new Rod();
		}
		rodArr[0]= check(list,"daiwa");
		rodArr[1] = check(list,"shimano");
		rodArr[2] = check(list,"banax");
		rodArr[3] = check(list,"ns");
		rodArr[4] = check(list,"eunsung");
		
		for(int i = 0 ; i < rodArr.length ; i++) {
			for(int j = 0; j < rodArr.length-1 ; j ++) {
				Rod tmp = null;
				if(rodArr[j].count>rodArr[j+1].count) {
					tmp = rodArr[j];
					rodArr[j]=rodArr[j+1];
					rodArr[j+1]=tmp;
				}
			}
		}
		
		for(int i = 0 ; i < 5 ; i++) {
		System.out.println(i+1 + "순위 낚싯대 브랜드 : " + rodArr[4-i].name+ "  |  개수 : " +  rodArr[4-i].count  ); 
		}
	}
		
		
	//브랜드 카운트기능
	private Rod check(List<FishUser> list , String rod) {
		Rod rod1 = new Rod();
		int count = 0;
	
		for(FishUser f : list) {
			if(f.getFishingRod1().indexOf(rod)!= -1) {
				count++;
			}
			if(f.getFishingRod2().indexOf(rod)!=-1) {
				count++;
			}
			if(f.getFishingRod3().indexOf(rod)!= -1) {
				count++;
			}
			if(f.getFishingRod4().indexOf(rod)!= -1) {
				count++;
			}
			if(f.getFishingRod5().indexOf(rod)!= -1) {
				count++;
			}
		}
		rod1.count = count;
		rod1.name = rod;
		return rod1;
	}
	
	//회원 전체 조회
	public void getUserList() {
		List<FishUser> list = FishDAO.getInstance().getUserList();
		
		for(FishUser f : list) {
			System.out.println(" ID : " +f.getId()+ " | PW : " +f.getPw() +  " | 이름 : " +f.getName()+ " | 닉네임 : " +f.getNickName()+  " | 전화번호 : "+f.getCustomerPhone()+ " | 등급 : "+f.getCustomerGrade()+ " | 신청 횟수 : " + f.getRepairCount() );
			System.out.println(" 낚싯대 1 : "+ f.getFishingRod1() + " |  낚싯대 2 : "+f.getFishingRod2()+" |  낚싯대 3 : "+f.getFishingRod3()+" | 낚싯대 4 : "+f.getFishingRod4()+ " | 낚싯대 5 : "+f.getFishingRod5());
			System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
//낚시대 정보 담기
class Rod{
	int count;
	String name;
	
	public Rod() {
		this.count = 0;
		this.name = "없음";
	}
}
