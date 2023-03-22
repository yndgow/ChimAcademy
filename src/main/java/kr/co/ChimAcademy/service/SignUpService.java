package kr.co.ChimAcademy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.repository.DepartmentRepo;
import kr.co.ChimAcademy.repository.MajorRepo;

@Service
public class SignUpService {

	private final DepartmentRepo departmentRepo;
	private final MajorRepo majorRepo;
	
	public SignUpService(DepartmentRepo departmentRepo, MajorRepo majorRepo) {
		this.departmentRepo = departmentRepo;
		this.majorRepo = majorRepo;
	}
	
	// 모든 학과 조회
	public List<DepartmentEntity> getDeparments() {
		return departmentRepo.findAll();
	}
	
	// 전공 조회
	public List<MajorEntity> getMajors(String depCode){
		return majorRepo.findByDepCode(depCode);
	}
	
	
	
	
}
