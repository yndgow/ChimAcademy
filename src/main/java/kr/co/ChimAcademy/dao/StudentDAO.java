package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.MemberVO;

@Repository
@Mapper
public interface StudentDAO {

	//정보 가져오기
	public MemberVO selectStudent(String uid);
	
	
	//수강내역 불러오기
	public List<MemberVO> selectLectures(String uid);
	
	
	//회원정보 수정
	public int updateStudent(MemberVO vo);
}
