package kr.co.ChimAcademy.service;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.Lec_SugangEntity;
import kr.co.ChimAcademy.entity.LectureEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.repository.DepartmentRepo;
import kr.co.ChimAcademy.repository.Lec_SugangRepo;
import kr.co.ChimAcademy.repository.LectureRepo;
import kr.co.ChimAcademy.repository.MajorRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignUpService {

	private final DepartmentRepo departmentRepo;
	private final MajorRepo majorRepo;
	private final Lec_SugangRepo sugangRepo;
	private final LectureRepo lectureRepo;
	
	public SignUpService(DepartmentRepo departmentRepo, MajorRepo majorRepo, Lec_SugangRepo sugangRepo, LectureRepo lectureRepo) {
		this.departmentRepo = departmentRepo;
		this.majorRepo = majorRepo;
		this.sugangRepo = sugangRepo;
		this.lectureRepo = lectureRepo;
	}
	
	// 모든 학과 조회
	public List<DepartmentEntity> getDeparments() {
		return departmentRepo.findAll();
	}
	
	// 전공 조회
	public List<MajorEntity> getMajors(String depCode){
		return majorRepo.findByDepCode(depCode);
	}
	
	// 수강 조건 체크(이미 신청한 과목인지, 수강 제한인원을 넘지 않는지)
	public boolean checkSugang(Lec_SugangEntity entity) {

		// 이미 신청했는지
		boolean chk1 = false;
		String uid = entity.getMemberEntity().getUid();
		int lecCode = entity.getLectureEntity().getLecCode();
		int result = sugangRepo.countByMemberEntityUidAndLectureEntityLecCode(uid, lecCode);
		log.info("result : " + result);
		chk1 = result > 0 ? false : true;
		log.info("chk1 : " + chk1);
		if(!chk1) {
			return false;
		}
				
		// 제한인원
		boolean chk2 = false;
		LectureEntity lectureEntity= lectureRepo.findById(entity.getLectureEntity().getLecCode()).orElse(null);
		int limit = lectureEntity.getLecLimit();
		int req = lectureEntity.getLecRequest();
		chk2 = limit > req ? true : false; 
		
		if(chk1 == true && chk2 == true) {
			return true;
		}else {
			return false;
		}
	}
	
	// 수강 신청 진행
	@Transactional
	public void insertSugang(Lec_SugangEntity entity) {
		
		// 수강 테이블 입력
		sugangRepo.save(entity);
		
		// 수강 인원 증가
		int lecCode = entity.getLectureEntity().getLecCode();
		LectureEntity lectureEntity = lectureRepo.findById(lecCode).orElseThrow();
		int cur = lectureEntity.getLecRequest();
		lectureEntity.setLecRequest(cur + 1);
		
	}
	
	
	// 수강 내역 가져오기
	public List<Lec_SugangEntity> getSugangs(String uid){
		return sugangRepo.findByMemberEntityUid(uid);
	}
	
	
	
}
