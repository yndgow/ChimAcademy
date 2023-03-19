package kr.co.ChimAcademy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lecture")
public class LectureEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lecCode;
	private String depCode;
	private String majorCode;
	private String lecName;
	private int lecClass;
	
	private int credit;
	private String lecDay;
	private int beginTime;
	private int endTime;
	private String lecLoc;
	private int lecLimit;
	private int lecRequest;
	private int lecGubun;
	
}
