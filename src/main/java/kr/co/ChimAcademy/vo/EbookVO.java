package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class EbookVO {
	private String bookId;
	private String GROUP;
	private String cate1;
	private String cate2;
	private String title;
	private String author;
	private String publisher;
	private String belong;
	private int loan;
	private int reserv;
	private int like;
	private String support;
	private String pubDate;
	private String bintro;
	private String aintro;
	private String index;
}
