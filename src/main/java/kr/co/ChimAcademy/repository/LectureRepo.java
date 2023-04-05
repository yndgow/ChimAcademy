package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.LectureEntity;

public interface LectureRepo extends JpaRepository<LectureEntity, Integer>{
		
}
