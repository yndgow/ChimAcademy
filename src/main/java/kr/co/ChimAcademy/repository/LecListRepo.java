package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.LectureEntity;

public interface LecListRepo extends JpaRepository<LecListEntity, Integer>{
	LecListEntity findByLectureEntity(LectureEntity entity);
}
