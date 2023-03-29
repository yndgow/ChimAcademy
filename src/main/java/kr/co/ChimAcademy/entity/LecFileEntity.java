package kr.co.ChimAcademy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "lec_file")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LecFileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	@Column(nullable = false)
	private int parent;
	@Column(nullable = false)
	private String newName;
	@Column(nullable = false)
	private String oriName;
	@Column
	@ColumnDefault("0")
	private int download;
	@Column
	@CreatedDate
	private LocalDateTime rdate;
}
