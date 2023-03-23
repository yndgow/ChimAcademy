package kr.co.ChimAcademy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemVO {
	private String library_tel;
    private String library_area;
    private String library_nm;
    private String library_addr;
    private String library_hompage;
}
