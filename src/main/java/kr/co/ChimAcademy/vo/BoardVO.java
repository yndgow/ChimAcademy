package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int no;
	private String depCode;
	private String lecCode;
	private String title;
	private String content;
	private String uid;
	private String rdate;
	private int hit;
	private int parent;
	private int regip;
	private String file;
	
	/* 추가필드 */
	private String name;
}
