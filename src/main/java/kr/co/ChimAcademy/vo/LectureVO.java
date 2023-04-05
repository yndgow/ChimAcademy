package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class LectureVO {
	private int lecCode;
	private String depCode;
	private String majorCode;
	private String lecName;
	private int lecClass;
	private int credit;
	private String lecDay;
	private int beginTime;
	private int endTime;
	private String lecLoc;
	private int lecLimit;
	private int lecRequest;
	private int lecGubun;
	
	// 추가필드 김지홍
	private String searchData;
	private String name;
}
