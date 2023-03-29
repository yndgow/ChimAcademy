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
public class CountVO {
	// 카테고리별 전자도서/오디오북 도서 갯수(/elib/ebook/_lnb에 이용) 
	private int c10;
	private int c11;
	private int c12;
	private int c13;
	private int c14;
	private int c15;
	private int c16;
	private int yes;
	private int book;
	private int kyobo;
	private int al;
}
