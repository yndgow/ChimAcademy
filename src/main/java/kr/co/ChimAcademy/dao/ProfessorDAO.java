package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.dto.EvalBoardDTO;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.vo.MemberVO;

@Repository
@Mapper
public interface ProfessorDAO {

	// 교수 내 정보 페이지
	public MemberVO selectProMy(String uid);
	
	// 교수 수강내역
	public List<LecSugangDto> selectProlecture(String uid);
	
	// 개인정보 수정
	public int updateProMy(MemberVO vo);
	
	// 개인정보 수정
	public int updateProMyinfo(MemberVO vo);
	
	//프로필 넣기
	public int updateProfile(String nName, String uid);
	
	// 강의평가 이동
	public List<EvalBoardDTO> selectEvals(String uid, int lecCode);
	
	// 강의평가 보기
	public EvalBoardDTO selectEvalView(int no);
}
