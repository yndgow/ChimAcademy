package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;

public interface MemberRepo extends JpaRepository<MemberEntity, String>{
	MemberUidMapping findByNameAndBirthAndHp(String name, String birth, String hp);
}
