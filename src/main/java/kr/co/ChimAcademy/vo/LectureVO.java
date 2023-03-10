package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class LectureVO {
	private String lecCode;
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
	private String lecGubun;
}
