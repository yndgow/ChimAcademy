package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;
import kr.co.ChimAcademy.vo.DepartmentVO;
import kr.co.ChimAcademy.vo.LectureVO;
import kr.co.ChimAcademy.vo.MemberVO;

@Mapper
@Repository
public interface AssistantDAO {
	DepartmentVO selectDep(String uid);
	List<LectureVO> selectLectures(LectureVO vo);
	List<BoardVO> selectNotices(int start);
	int countNotice();
	
	public List<MemberVO> selectMembers(String depCode);
	public MemberVO selectMember(String uid);
	public int updateMember(MemberVO vo);
}
