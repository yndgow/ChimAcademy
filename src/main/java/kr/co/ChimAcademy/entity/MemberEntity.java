package kr.co.ChimAcademy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "member")
public class MemberEntity {
	@Id
	private String uid;
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
	private String rdate;
	private String info;
	private String profile;
	private String member2;
	private String member3;
}
