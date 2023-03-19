package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.ProfessorEntity;

public interface ProfessorRepo extends JpaRepository<ProfessorEntity, Integer>{

}
