package kr.co.ChimAcademy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lec_list")
public class LecListEntity {
	
	@Id
	private int no;
	
	@ManyToOne
	@JoinColumn(name = "uid", referencedColumnName = "uid")
	private MemberEntity memberEntity;
	
	@OneToOne
	@JoinColumn(name = "lecCode", referencedColumnName = "lecCode")
	private LectureEntity lectureEntity;
	
}
