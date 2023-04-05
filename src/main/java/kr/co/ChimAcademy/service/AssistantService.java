package kr.co.ChimAcademy.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.AssistantDAO;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.LectureEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.repository.LecListRepo;
import kr.co.ChimAcademy.repository.LectureRepo;
import kr.co.ChimAcademy.repository.MajorRepo;
import kr.co.ChimAcademy.repository.MemberRepo;
import kr.co.ChimAcademy.vo.DepartmentVO;
import kr.co.ChimAcademy.vo.LectureVO;
import kr.co.ChimAcademy.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AssistantService {

	private final AssistantDAO dao;
	private final MemberRepo memberRepo;
	private final PasswordEncoder encoder;
	private final MajorRepo majorRepo;
	private final LectureRepo lectureRepo;
	private final LecListRepo lecListRepo;
	

	public AssistantService(AssistantDAO dao, MemberRepo memberRepo, PasswordEncoder encoder, MajorRepo majorRepo, LectureRepo lectureRepo, LecListRepo lecListRepo) {
		this.dao = dao;
		this.memberRepo = memberRepo;
		this.encoder = encoder;
		this.majorRepo = majorRepo;
		this.lectureRepo = lectureRepo;
		this.lecListRepo = lecListRepo;
	}

	// 학과 이름 가져오기
	public DepartmentVO selectDep(String uid) {
		return dao.selectDep(uid);
	}

	// 멤버 여러건 추가하기(JPA, 비밀번호 자동 넣기 불편)
	public List<MemberEntity> insertMembers(List<MemberEntity> members) {

		for (MemberEntity member : members) {
			SecureRandom random = new SecureRandom();
			byte[] bytes = new byte[12];
			random.nextBytes(bytes);
			String pass = Base64.getEncoder().encodeToString(bytes);
			pass = encoder.encode(pass);
			member.setPass(pass);
			member.setStatus(1);
		}
		return memberRepo.saveAll(members);
	}
	
	// 학과코드로 전공코드찾기
	public List<MajorEntity> selectMajors(String depCode){
		return majorRepo.findByDepCode(depCode);
	}
	
	// 조교 교과목 검색
	public List<LectureVO> selectLectures(LectureVO vo){
		log.info("lecutres : " + dao.selectLectures(vo));
		return dao.selectLectures(vo);
	}
	
	//조교 학생목록 출력
	public List<MemberVO> selectMembers(String depCode){
		return dao.selectMembers(depCode);
	}
	
	//조교 학생 수정
	public MemberVO selectMember(String uid){
		return dao.selectMember(uid);
	}
	public int updateMember(MemberVO vo) {
		return dao.updateMember(vo);
	}
	
	
	// 학과별 교수
	public List<MemberEntity> selectProfessors(DepartmentEntity departmentEntity){
		
		return memberRepo.findByDepartmentEntityAndLevel(departmentEntity, 3);
	}
	
	// 강의 등록
	public void insertLecture(LectureEntity entity) {
		lectureRepo.save(entity);
	}

	
	// 교수 - 강의 등록
	@Transactional
	public void insertLectureList(LectureEntity lectureEntity, MemberEntity memberEntity) {
		LecListEntity entity = new LecListEntity();
		
		LectureEntity lecEntity = lectureRepo.save(lectureEntity);
		
		entity.setLectureEntity(lecEntity);
		entity.setMemberEntity(memberEntity);
		
		lecListRepo.save(entity);
	}
	
	// 과목 1개 담당교수가 있는
	public LecListEntity getLecture(LectureEntity entity) {
		return lecListRepo.findByLectureEntity(entity);
	}
	
	// 과목 1개 담당교수가 없는
	public LectureEntity getLectureEn(int lecCode) {
		return lectureRepo.findById(lecCode).orElse(null);
	}
	
	
	// 과목 수정 담당교수가 있는
	@Transactional
	public void updateLecture(LectureEntity lectureEntity, MemberEntity memberEntity, int no) {
		// 과목 업데이트
		lectureRepo.save(lectureEntity);
		// no가 있으면 찾아서 업데이트 없으면 새로 만든 후 저장
		LecListEntity lecListEntity = lecListRepo.findById(no).orElse(new LecListEntity());
		lecListEntity.setLectureEntity(lectureEntity);
		lecListEntity.setMemberEntity(memberEntity);
		lecListRepo.save(lecListEntity);
	}
	
	// 과목 삭제
	public void deleteLecture(int lecCode) {
		lectureRepo.deleteById(lecCode);
	}
	
	
	
	
}
