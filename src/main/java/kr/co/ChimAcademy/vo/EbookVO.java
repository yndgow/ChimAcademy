package kr.co.ChimAcademy.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EbookVO {
	private String bookId;
	private String GROUP;
	private int cate1;
	private int cate2;
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
	private String rdate;
	private String applier;
	private String thumb;
	
	private MultipartFile tname;
	private MultipartFile fname;
	private String c1Name;
	private String c2Name;
}
