package kr.co.ChimAcademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.Lec_SugangEntity;

public interface Lec_SugangRepo extends JpaRepository<Lec_SugangEntity, Integer>{
	
	// 수강여부확인
	int countByMemberEntityUidAndLectureEntityLecCode(String uid, int lecCode);
	
	// 수강내역 가져오기
	List<Lec_SugangEntity> findByMemberEntityUid(String uid);
}
