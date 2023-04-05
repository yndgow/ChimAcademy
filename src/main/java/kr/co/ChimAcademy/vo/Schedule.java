package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class Schedule {
	private int no;
	private String uid;
	private String bookId;
	private String loadDate;
	private String returnDate;
}
