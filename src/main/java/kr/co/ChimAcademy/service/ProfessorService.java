package kr.co.ChimAcademy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.ProfessorDAO;
import kr.co.ChimAcademy.vo.MemberVO;

@Service
public class ProfessorService {
	@Autowired
	private ProfessorDAO dao;
	
	// 교수 내 정보 페이지
	public MemberVO selectPro(String uid) {
		return dao.selectPro(uid);
	}
}
