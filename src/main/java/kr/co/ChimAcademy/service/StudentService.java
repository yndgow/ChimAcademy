package kr.co.ChimAcademy.service;



import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ChimAcademy.dao.StudentDAO;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.entity.LecFileEntity;
import kr.co.ChimAcademy.repository.BoardRepo;
import kr.co.ChimAcademy.repository.LecFileRepo;
import kr.co.ChimAcademy.vo.EvalBoardVO;
import kr.co.ChimAcademy.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentService {
	
	@Autowired
	private StudentDAO dao;
	@Autowired
	private BoardRepo boardRepo;
	@Autowired
	private LecFileRepo fileRepo;

	
	public MemberVO selectStudent(String uid) {
		
		return dao.selectStudent(uid);
	}
	
	// 수강 신청내역(수강신청페이지) - 구홍모
	public List<LecSugangDto> selectSugangs(String uid){
		return dao.selectSugangs(uid);
	};
	
	//수강내역 불러오기
	public List<LecSugangDto> selectLectures(String uid){
		
		return dao.selectLectures(uid);
	};
	
	//회원정보 수정하기
	public int updateStudent(MemberVO vo) {
		
		return dao.updateStudent(vo);
	}
	
	//강의평가 넣기
	public int insertLecEval(EvalBoardVO vo) {
		return dao.insertLecEval(vo);
	};

	// 프로필 업데이트
	public String updateProfile(MemberVO vo) {
		// 프로파일 업로드
		String nName = fileUpload(vo);
		dao.updateProfile(nName, vo.getUid());
		return nName;
	}
	
	// 파일 업로드
	public String fileUpload(MemberVO vo) {
		// 썸네일 파일 첨부 
		MultipartFile thumb = vo.getProfileThumb();
		String nName = null;
		if(!thumb.isEmpty()) {
				// 새 파일명 생성
				String oName = thumb.getOriginalFilename();
				String ext = oName.substring(oName.lastIndexOf("."));
				nName = UUID.randomUUID().toString()+ext;
				String path = null;
				// 시스템 경로
					path = new File("profileThumb/").getAbsolutePath();
				// 파일 저장
				try {
					thumb.transferTo(new File(path, nName));
				} catch (IllegalStateException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
				
			}
		return nName;
	}

	// 강의계획서 가져오기
	public BoardEntity selectSyllabus(int lecCode) {
		return boardRepo.findByLecCode(lecCode);
	}
	
	// 계획서 파일 정보 가져오기
	public LecFileEntity selectFile(int no) {
		return fileRepo.findById(no).orElse(null);
	}
	
}
