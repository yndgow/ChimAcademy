package kr.co.ChimAcademy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.MemberVO;

@Repository
@Mapper
public interface ProfessorDAO {

	// 교수 내 정보 페이지
	public MemberVO selectPro(String uid);
}
