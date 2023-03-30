package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class ScoreVO {
	private int no;
	private String uid;
	private int lecCode;
	private int midExam;
	private int finalExam;
	private int etc1;
	private int etc2;
	private int etc3;
	private int etc4;
	private int totalScore;
	private String sGrade;
}
