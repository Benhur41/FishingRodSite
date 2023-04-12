package com.home.exe;

import java.util.List;

import com.home.fishDAO.FishService;
import com.yedam.community.CommDAO;
import com.yedam.community.Community;

public class Application {
	public static void main(String[] args) {
		new FishExe();
//		List<Community> list2 = CommDAO.getInstance().getCommList();
//		for(int i = 0 ; i < list2.size(); i++) {
//			for(int j = 0 ; j < list2.size()-1; j++) {
//				if(list2.get(j).getRecommand()<list2.get(j+1).getRecommand()) {
//					Community tmp = list2.get(j);
//					list2.add(j,list2.get(j+1));
//					list2.add(j+1,tmp);
//					
//				}
//			}
//		}
//		
//		for(Community c : list2) {
//			System.out.println(c.getCoNum() +" | "+c.getRecommand());
//		}
		
	}
	
}
