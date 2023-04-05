package kr.co.ChimAcademy.service;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.StudentDAO;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.Lec_SugangEntity;
import kr.co.ChimAcademy.entity.LectureEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.entity.ScoreEntity;
import kr.co.ChimAcademy.repository.DepartmentRepo;
import kr.co.ChimAcademy.repository.Lec_SugangRepo;
import kr.co.ChimAcademy.repository.LectureRepo;
import kr.co.ChimAcademy.repository.MajorRepo;
import kr.co.ChimAcademy.repository.ScoreRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignUpService {

	private final DepartmentRepo departmentRepo;
	private final MajorRepo majorRepo;
	private final Lec_SugangRepo sugangRepo;
	private final LectureRepo lectureRepo;
	private final StudentDAO studentDAO;
	private final ScoreRepo scoreRepo;
	
	public SignUpService(DepartmentRepo departmentRepo, MajorRepo majorRepo, Lec_SugangRepo sugangRepo, LectureRepo lectureRepo, StudentDAO studentDAO, ScoreRepo scoreRepo) {
		this.departmentRepo = departmentRepo;
		this.majorRepo = majorRepo;
		this.sugangRepo = sugangRepo;
		this.lectureRepo = lectureRepo;
		this.studentDAO = studentDAO;
		this.scoreRepo = scoreRepo;
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
		
		log.info("chk2 : " + chk2);
		if(chk1 == true && chk2 == true) {
			return true;
		}else {
			return false;
		}
	}
	
	// 수강 신청 진행
	@Transactional
	public int insertSugang(Lec_SugangEntity entity) {
		
		int credit = entity.getLectureEntity().getCredit();
		int curCredit = studentDAO.sumCredit(entity.getMemberEntity().getUid());
		if(credit + curCredit > 20) {
			return 2;
		}else {
			// 수강 테이블 입력
			sugangRepo.save(entity);
			
			// 수강 인원 증가
			int lecCode = entity.getLectureEntity().getLecCode();
			LectureEntity lectureEntity = lectureRepo.findById(lecCode).orElseThrow();
			int cur = lectureEntity.getLecRequest();
			lectureEntity.setLecRequest(cur + 1);
			
			// 성적 테이블 추가
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			
			ScoreEntity scoreEntity = new ScoreEntity();
			scoreEntity.setYear(year);
			scoreEntity.setMemberEntity(entity.getMemberEntity());
			scoreEntity.setLectureEntity(entity.getLectureEntity());
			
			scoreRepo.save(scoreEntity);
			
			return 1;
		}
	}
	
	
	// 수강 내역 가져오기
	public List<LecSugangDto> getSugangs(String uid){
		return studentDAO.selectSugangs(uid);
	}
	
	// 학생별 총 신청 학점
	public int sumCredit(String uid) {
		int result = studentDAO.sumCredit(uid);
		return result;
	}
	
	// 수강내역 삭제
	@Transactional
	public void delSugang(String uid, int lecCode) {
		sugangRepo.deleteByMemberEntityUidAndLectureEntityLecCode(uid, lecCode);
	}
	
	// 수강 인원 감소
	@Transactional
	public void decreSugang(int lecCode) {
		LectureEntity lectureEntity = lectureRepo.findById(lecCode).orElseThrow();
		int reqCount = lectureEntity.getLecRequest();
		lectureEntity.setLecRequest(reqCount-1);
		
	}
	
	// 수강 시간표 겹치기 여부 체크
	public boolean checkDuplicationLecTime(Lec_SugangEntity entity) {
		// 조건 판단 변수
		boolean checkDup = true;
		
		// 신청하려는 요일
		String nDay = entity.getLectureEntity().getLecDay();
		
		List<Lec_SugangEntity> sugangs = sugangRepo.findByMemberEntityUid(entity.getMemberEntity().getUid());
		for(Lec_SugangEntity lec : sugangs) {
			// 이미 신청한 요일
			String oDay = lec.getLectureEntity().getLecDay();
			
			// 둘의 요일이 같다면
			if(nDay.equals(oDay)) {
				int oBegin = lec.getLectureEntity().getBeginTime();
				int oEnd= lec.getLectureEntity().getEndTime();
				
				int nBegin = entity.getLectureEntity().getBeginTime();
				int nEnd = entity.getLectureEntity().getEndTime();
				
				
				// 이미 신청한 시작시간 부터 끝나는 시간까지
				for(int i = oBegin; i <= oEnd; i++) {
					// 새로 신청한 시작시간부터 끝나는 시간까지
					for(int j = nBegin; j <= nEnd; j++) {
						// 둘의 시간에서 겹치는 시간이 발생한다면 신청 불가
						if(i == j) {
							checkDup = false;
							break;
						}
					}
					if(!checkDup) {
						break;
					}
				}
						
			}
		}
		
		return checkDup;
	}
	
}
