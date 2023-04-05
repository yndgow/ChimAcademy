package kr.co.ChimAcademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;

public interface MemberRepo extends JpaRepository<MemberEntity, String>{
	// 멤버 정보 가져오기
	MemberUidMapping findByNameAndBirthAndHp(String name, String birth, String hp);
	
	// 이메일 중복 여부
	int countByEmail(String email);
	
	// 학과별 교수 가져오기
	List<MemberEntity> findByDepartmentEntityAndLevel(DepartmentEntity departmentEntity, int level);
}
