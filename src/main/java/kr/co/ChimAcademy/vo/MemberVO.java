package kr.co.ChimAcademy.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberVO {
	private String uid;
	private String pass;
	private String name;
	private String birth;
	private String depCode;
	private String majorCode;
	private int sYear;
	private int gender;
	private int totCredit;
	private int avgCredit;
	private String hp;
	private String email;
	private String status;
	private int level;
	private String zip;
	private String addr1;
	private String addr2;
	private String regip;
	private String wdate;
	private String rdate;
	private String info;
	private String profile;
	private MultipartFile profileThumb;
	private String member2;
	private String member3;
	
  // 추가필드 통합 김지홍
	//추가필드
	private String majorName;
	private String lecName;
	private int lecCode;
	//private String totalScore;
	private int credit;
	//private String sGrade;
	

//	교수-추가필드
	private String depName;
	private String career;
	private String lab;
	private String csTime;
	private String lecClass;
	private String lecGubun;
	private String beginTime;
	private String endTime;
	private String lecDay;
	private String lecLoc;

}
