package kr.co.ChimAcademy.dto;

import lombok.Data;


// 아이디별 수강 내역 or 강의 내역
@Data
public class LecSugangDto {

	private String pid;
	private int lecCode;
	private int lecClass;
	private String lecName;
	private int lecGubun;
	private int credit;
	private String name;
	private String lecDay;
	private int beginTime;
	private int endTime;
	private String lecLoc;
	private String sGrade;
	private int result;
	
	
	
	// 수강인원
	private int lecRequest;
	
	// 현재 수강 총학점
	private int sumCredit;
	
	// 시간 순차 출력
	private String cTime;
	
	// 조건에 맞게 출력
	private String gubun;
	
	public String getGubun() {
		int gubun = this.lecGubun;
		if(gubun == 0) {
			this.gubun = "-";
		}else if(gubun == 1) {
			this.gubun = "전공필수";
		}else if(gubun == 2) {
			this.gubun = "전공선택";
		}else if(gubun == 3) {
			this.gubun = "교양";
		}
		
		return this.gubun;
	}
	
	public String getCTime() {
		this.cTime = "";
		for(int i = this.beginTime; i <= this.endTime; i++) {
			this.cTime += i;
		}
		if(cTime.equals("1")) {
			cTime = "";
		}
		return cTime;
	}
	
	public String getLecDay() {
		if(this.lecDay == null) {
			return "";
		}else {
			return this.lecDay;
		}
	}
}
