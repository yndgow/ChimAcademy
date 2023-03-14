package kr.co.ChimAcademy.service;

import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;
import kr.co.ChimAcademy.repository.MemberRepo;

@Service
public class MemberService {
	
	private final MemberRepo memberRepo;

	public MemberService(MemberRepo memberRepo) {
		this.memberRepo = memberRepo;
	}
	
	
	// 학번, 아이디 찾기
	public MemberUidMapping searchUid(String name, String birth, String hp) {
		return memberRepo.findByNameAndBirthAndHp(name, birth, hp);
	}
	
	
	

}
