package kr.co.ChimAcademy.service;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ChimAcademy.dao.StudentDAO;
import kr.co.ChimAcademy.vo.EbookFileVO;
import kr.co.ChimAcademy.vo.EbookVO;
import kr.co.ChimAcademy.vo.MemberVO;
import kr.co.ChimAcademy.vo.infoFileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentService {
	
	@Autowired
	private StudentDAO dao;

	
	public MemberVO selectStudent(String uid) {
		
		return dao.selectStudent(uid);
	}
	
	//수강내역 불러오기
	public List<MemberVO> selectLectures(String uid){
		
		return dao.selectLectures(uid);
	};
	
	//회원정보 수정하기
	public int updateStudent(MemberVO vo) {
		
		return dao.updateStudent(vo);
	}
	

	// 프로필 업데이트
	public int insertProfile(MemberVO vo) {
		// 프로파일 업로드
		infoFileVO fvo = fileUpload(vo);
		
		return dao.insertProfile(fvo);
	}
	// 파일 업로드
	public infoFileVO fileUpload(MemberVO vo) {
		// 썸네일 파일 첨부 
		MultipartFile thumb = vo.getProfileThumb();
		infoFileVO fvo = null;
			if(!thumb.isEmpty()) {
				// 새 파일명 생성
				String oName = thumb.getOriginalFilename();
				String ext = oName.substring(oName.lastIndexOf("."));
				String nName = UUID.randomUUID().toString()+ext;
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
				
				 fvo = infoFileVO.builder()
												.parent(vo.getUid())
												.oriName(oName)
												.newName(nName)
												.build();
			}
		return fvo;
	}

	
}
