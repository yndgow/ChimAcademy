package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.vo.EvalBoardVO;
import kr.co.ChimAcademy.vo.MemberVO;
import kr.co.ChimAcademy.vo.infoFileVO;

@Repository
@Mapper
public interface StudentDAO {

	//정보 가져오기
	public MemberVO selectStudent(String uid);
	
	
	//수강내역 불러오기
	public List<LecSugangDto> selectLectures(String uid);
	
	
	//회원정보 수정
	public int updateStudent(MemberVO vo);
	
	// 수강 신청내역(수강신청페이지) 
	List<LecSugangDto> selectSugangs(String uid);
	
	//프로필 넣기
	public int updateProfile(String nName, String uid);
	
	// 학생별 총 신청학점
	int sumCredit(String uid);
	
	//강의평가 넣기
	public int insertLecEval(EvalBoardVO vo);

}
