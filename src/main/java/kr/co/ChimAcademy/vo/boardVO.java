package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class boardVO {
	private int no;
	private String depCode;
	private String lecName;
	private String title;
	private String content;
	private String uid;
	private String rdate;
	private int hit;
	private int parent;
	private int regip;
	private String file;
}
