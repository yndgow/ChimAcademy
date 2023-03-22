package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.MemberVO;

@Repository
@Mapper
public interface ProfessorDAO {

	// 교수 내 정보 페이지
	public MemberVO selectProMy(String uid);
	
	// 교수 수강내역
	public List<MemberVO> selectProlecture(String uid);
}
