package kr.co.ChimAcademy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.MajorEntity;

public interface MajorRepo extends JpaRepository<MajorEntity, String>{

	List<MajorEntity> findByDepCode(String depCode);
}
