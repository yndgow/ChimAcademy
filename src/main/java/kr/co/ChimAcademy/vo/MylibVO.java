package kr.co.ChimAcademy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MylibVO {
	private int no;
	private String uid;
	private String bookId;
	private int state;
	private String loanDate;
	private String returnDate;
	private String returnReal;
	
	//add
	private EbookVO EbookVO;
}
