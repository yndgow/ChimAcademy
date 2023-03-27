package kr.co.ChimAcademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.LectureEntity;

public interface LecListRepo extends JpaRepository<LecListEntity, Integer>{
	// 과목찾기
	LecListEntity findByLectureEntity(LectureEntity entity);
	
	// 교수 - 강의 목록
	List<LecListEntity> findByMemberEntityUid(String uid);
	}
