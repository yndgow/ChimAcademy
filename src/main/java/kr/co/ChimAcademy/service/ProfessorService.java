package kr.co.ChimAcademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.ProfessorDAO;
import kr.co.ChimAcademy.vo.MemberVO;

@Service
public class ProfessorService {
	@Autowired
	private ProfessorDAO dao;
	
	// 교수 내 정보 페이지
	public MemberVO selectProMy(String uid) {
		return dao.selectProMy(uid);
	}
	
	// 교수 수강내역
	public List<MemberVO> selectProlecture(String uid){
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
}
