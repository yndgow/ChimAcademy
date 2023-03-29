package kr.co.ChimAcademy.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SyllabusDto {
	
	private String title;
	private int lecCode;
	private MultipartFile profFile;
	private String content;
	private String uid;
	private int type;
}
