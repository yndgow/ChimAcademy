package kr.co.ChimAcademy.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.StudentDAO;
import kr.co.ChimAcademy.vo.MemberVO;

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


	
}
