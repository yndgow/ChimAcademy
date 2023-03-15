package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;

public interface MemberRepo extends JpaRepository<MemberEntity, String>{
	// 멤버 정보 가져오기
	MemberUidMapping findByNameAndBirthAndHp(String name, String birth, String hp);
	
	// 이메일 중복 여부
	int countByEmail(String email);
}
