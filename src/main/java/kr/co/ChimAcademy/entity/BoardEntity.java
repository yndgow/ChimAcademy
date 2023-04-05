package kr.co.ChimAcademy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name ="board")
public class BoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	@Column(nullable = true)
	private String depCode;
	@Column(nullable = true)
	private Integer lecCode;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String content;
	@CreatedDate
	private LocalDateTime rdate;
	private int hit;
	private int parent;
	private String regip;
//	private int file;
	private int good;
	private int bad;
	private int type;
	
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "uid", referencedColumnName = "uid")
	private MemberEntity memberEntity;
	
	 
	@OneToOne
	@JoinColumn(name= "file", referencedColumnName = "no", nullable = true)
	private LecFileEntity fileEntity; 
	
}
