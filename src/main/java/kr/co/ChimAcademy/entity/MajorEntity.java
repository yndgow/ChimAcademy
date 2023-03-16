package kr.co.ChimAcademy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "major")
public class MajorEntity {
	@Id
	private String majorCode;
	private String majorName;
	private String depCode;
	
}
