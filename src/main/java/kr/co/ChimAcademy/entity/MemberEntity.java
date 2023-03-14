package kr.co.ChimAcademy.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class MemberEntity {
	@Id
	private String uid;
	@JsonIgnore
	private String pass;
	private String name;
	private String birth;
	private String depCode;
	private String majorCode;
	private int sYear;
	private int gender;
	private int totCredit;
	private int avgCredit;
	private String hp;
	private String email;
	private String status;
	private int level;
	private String zip;
	private String addr1;
	private String addr2;
	private String regip;
	private String wdate;
	@LastModifiedDate
	private LocalDateTime rdate;
	private String info;
	private String profile;
	private String member2;
	private String member3;
}
