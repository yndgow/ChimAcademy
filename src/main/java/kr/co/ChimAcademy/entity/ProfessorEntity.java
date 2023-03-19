package kr.co.ChimAcademy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "professor")
public class ProfessorEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	
//	private String uid;
	private String career;
	private String lab;
	
	@OneToOne
	@JoinColumn(name = "uid")
	private MemberEntity memberEntity;
}
