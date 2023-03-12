package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class MajorVO {
	private String uid;
	private String pass;
	private String name;
	private String brith;
	private String depCode;
	private String majorCode;
	//private int class; -에러 때문에 주석처리 구홍모
	private int gender;
	private int totalCredit;
	private int averageCredit;
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
	private String member2;
	private String member3;
}
