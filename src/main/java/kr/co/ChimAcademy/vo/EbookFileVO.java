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
public class EbookFileVO {
	private int fno;
	private String parent;
	private String newName;
	private String oriName;
	private int download;
	private String rdate;
}
