package kr.co.ChimAcademy.dto;

import lombok.Data;

@Data
public class LecSugangDto {

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
}
