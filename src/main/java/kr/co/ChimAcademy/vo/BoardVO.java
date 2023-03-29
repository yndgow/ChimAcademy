package kr.co.ChimAcademy.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardVO {
	private int no;
	private String depCode;
	private int lecCode;
	private String title;
	private String content;
	private String uid;
	private String rdate;
	private int hit;
	private int parent;
	private String regip;
	private int file;
	private int good;
	private int bad;
	// type 추가 0: 일반글(기본값), 1: 공지사항, 2: 강의계획서
	private int type;
	
	/* 추가필드 */
	private String name;
	private int commentsCount;
}
