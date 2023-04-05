package kr.co.ChimAcademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.DepartmentEntity;

public interface DepartmentRepo extends JpaRepository<DepartmentEntity, String>{

}
