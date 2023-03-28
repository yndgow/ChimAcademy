package kr.co.ChimAcademy.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ChimAcademy.dao.ProfessorDAO;
import kr.co.ChimAcademy.dto.EvalBoardDTO;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.repository.LecListRepo;
import kr.co.ChimAcademy.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfessorService {
	@Autowired
	private ProfessorDAO dao;

	@Autowired
	private LecListRepo lecListRepo;

	// 교수 내 정보 페이지
	public MemberVO selectProMy(String uid) {
		return dao.selectProMy(uid);
	}

	// 교수 수강내역
	public List<LecSugangDto> selectProlecture(String uid) {
		return dao.selectProlecture(uid);
	}

	// 개인정보 수정
	public int updateProMy(MemberVO vo) {
		return dao.updateProMy(vo);
	}
	// 개인정보 수정2

	public int updateProMyinfo(MemberVO vo) {
		return dao.updateProMyinfo(vo);
	}

	public List<LecListEntity> selectClasss(String uid) {
		return lecListRepo.findByMemberEntityUid(uid);
	}

	/* ::::::::::프로필 사진:::::::::: */
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
		if (!thumb.isEmpty()) {
			// 새 파일명 생성
			String oName = thumb.getOriginalFilename();
			String ext = oName.substring(oName.lastIndexOf("."));
			nName = UUID.randomUUID().toString() + ext;
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

	// 강의 평가 이동
	public List<EvalBoardDTO> selectEvals(String uid, int lecCode) {
		return dao.selectEvals(uid, lecCode);
	}

	// 강의평가 보기
	public EvalBoardDTO selectEvalView(int no) {
		return dao.selectEvalView(no);
	}
}
