package kr.co.ChimAcademy.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ChimAcademy.dao.ProfessorDAO;
import kr.co.ChimAcademy.dto.EvalBoardDTO;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.dto.SyllabusDto;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.entity.LecFileEntity;
import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.ScoreEntity;
import kr.co.ChimAcademy.repository.BoardRepo;
import kr.co.ChimAcademy.repository.LecFileRepo;
import kr.co.ChimAcademy.repository.LecListRepo;
import kr.co.ChimAcademy.repository.MemberRepo;
import kr.co.ChimAcademy.repository.ScoreRepo;
import kr.co.ChimAcademy.vo.MemberVO;
import kr.co.ChimAcademy.vo.ScoreVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfessorService {
	@Autowired
	private ProfessorDAO dao;

	@Autowired
	private LecListRepo lecListRepo;
	
	@Autowired
	private BoardRepo  boardRepo;

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private LecFileRepo fileRepo;
	
	@Autowired
	private ScoreRepo scoreRepo;
	
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
	
	// 강의 계획서 & 파일 업로드
	@Transactional
	public void insertBoardSyllabus(SyllabusDto dto) {
		MultipartFile dtoFile = dto.getProfFile();
		if(!dtoFile.isEmpty()) {
			// 현재 연도 가져오기
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			// 연도별 폴더 구조
			
			Path uploadDir = Paths.get("file/"+year+"/");
			
	        String uuid = UUID.randomUUID().toString();
	        String originalFilename = dtoFile.getOriginalFilename();
	        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	        String newFileName = uuid + fileExtension;
	    	Path destinationPath = uploadDir.resolve(newFileName);
	    	
			try {
				// 업로드 폴더의 존재여부 확인 없으면 생성
				Files.createDirectories(uploadDir);
				// inputStream 이용시 close 하기
				InputStream inputStream = dtoFile.getInputStream();
				Files.copy(inputStream, destinationPath);
				inputStream.close();
			}catch (IOException e) {
				log.error(e.getMessage());
			}

			// dto 에서 board 나누기
			BoardEntity oriBoardEntity =  BoardEntity.builder()
											.lecCode(dto.getLecCode())
											.title(dto.getTitle())
											.content(dto.getContent())
											.memberEntity(memberRepo.findById(dto.getUid()).get())
											.type(2)
											.build();
			
			// 보드 insert 리턴 값은 새로생성된 no를 가지고 있음
			BoardEntity boardEntity = boardRepo.save(oriBoardEntity);
			
			// dto 에서 파일 나누기 위의 no 넣기
			LecFileEntity fileEntity = LecFileEntity.builder()
					.newName(newFileName)
					.oriName(dtoFile.getOriginalFilename())
					.parent(boardEntity.getNo())
					.build();
			// 파일 insert
			fileRepo.save(fileEntity);
			// 보드 파일번호 업데이트
			boardEntity.setFileEntity(fileEntity);
		}
	}
	
	// 수강 인원 정보 출력
	public List<ScoreEntity> selectScoresBylecCode(int lecCode){
		return scoreRepo.findByLectureEntityLecCode(lecCode);
	}
	
	// 수강 인원 정보 1명 출력
	public ScoreEntity selectScore(int no) {
		return scoreRepo.findById(no).orElse(null);
	}
	
	// 성적 입력하기
	@Transactional
	public void updateScore(ScoreVO vo) {
		ScoreEntity entity= scoreRepo.findById(vo.getNo()).orElse(new ScoreEntity());
		entity.setMidExam(vo.getMidExam());
		entity.setFinalExam(vo.getFinalExam());
		entity.setEtc1(vo.getEtc1());
		entity.setEtc2(vo.getEtc2());
		entity.setEtc3(vo.getEtc3());
		entity.setEtc4(vo.getEtc4());
		entity.setTotalScore(vo.getTotalScore());
		entity.setGrade(vo.getSGrade());
		scoreRepo.save(entity);
	}
	
}
