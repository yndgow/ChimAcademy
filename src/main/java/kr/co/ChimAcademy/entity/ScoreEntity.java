package kr.co.ChimAcademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "score")
public class ScoreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	@Column(name="sYear")
	private int year;
//	private String uid;
//	private int lecCode;
	private int midExam;
	private int finalExam;
	private int etc1;
	private int etc2;
	private int etc3;
	private int etc4;
	@Column(name = "totalScore")
	private int totalScore;
	@Column(name = "sGrade")
	private String grade;
	
	public int getTotalScore() {
		this.totalScore= this.midExam + this.finalExam + this.etc1 + this.etc2 + this.etc3 + this.etc4;
		return this.totalScore;
	}
	
	public String getGrade() {
		int sc = this.totalScore;
		
		if(sc >= 95) {
			this.grade = "A+";
		}else if(sc >= 90 && sc < 95) {
			this.grade = "A";
		}else if(sc >= 85 && sc < 90) {
			this.grade = "B+";
		}else if(sc >= 80 && sc < 85) {
			this.grade = "B";
		}else if(sc >= 75 && sc < 80) {
			this.grade = "C+";
		}else if(sc >= 70 && sc < 75) {
			this.grade = "C";
		}else{
			this.grade = "F";
		}
		
		return this.grade;
	}
	
	@ManyToOne
	@JoinColumn(name = "uid", columnDefinition = "uid")
	private MemberEntity memberEntity;
	
	@ManyToOne
	@JoinColumn(name = "lecCode", columnDefinition = "lecCode")
	private LectureEntity lectureEntity;
	
}
