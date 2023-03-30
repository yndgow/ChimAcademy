package kr.co.ChimAcademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.ScoreEntity;

public interface ScoreRepo extends JpaRepository<ScoreEntity, Integer>{
	List<ScoreEntity> findByLectureEntityLecCode(int lecCode);
}
