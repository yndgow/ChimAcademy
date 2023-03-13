package kr.co.ChimAcademy.vo;

import lombok.Data;

@Data
public class MylibVO {
	private int no;
	private String uid;
	private String bookId;
	private String loanDate;
	private String returnDate;
	private String returnReal;
}
