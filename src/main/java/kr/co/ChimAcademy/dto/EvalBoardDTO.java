package kr.co.ChimAcademy.dto;

import lombok.Data;

@Data
public class EvalBoardDTO {
	private int no;
	private int lecCode;
	private String uid;
	private int evalScore;
	private String content;
	private String rdate;
	
	private int lecClass;
	private String lecName;
	private int credit;
	private int lecGubun;
	
	//
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
}
