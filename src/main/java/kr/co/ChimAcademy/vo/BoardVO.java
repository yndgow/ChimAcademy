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
	private String file;
	private int good;
	private int bad;
	
	/* 추가필드 */
	private String name;
}
