package kr.co.ChimAcademy.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lec_sugang")
public class Lec_SugangEntity {
	@Id
	private int no;
	
	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid")
	private MemberEntity memberEntity;
	
	@ManyToOne
	@JoinColumn(name = "lecCode", referencedColumnName = "lecCode")
	private LectureEntity lectureEntity;
	
}
