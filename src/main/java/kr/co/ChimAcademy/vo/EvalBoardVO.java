package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class EvalBoardVO {
	private int no;
	private int lecCode;
	private String uid;
	private String sUid;
	private String content;
	private String rdate;
	private int evalScore;
	private int result;
}
