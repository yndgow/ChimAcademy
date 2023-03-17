package kr.co.ChimAcademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table(name ="board")
public class BoardEntity {
	@Id
	private int no;
	private String depCode;
	private String lecCode;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String content;
	//private String uid;
	private String rdate;
	private int hit;
	private int parent;
	private String regip;
	private String file;
	private int good;
	private int bad;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private MemberEntity memberEntity;
	
	
}
