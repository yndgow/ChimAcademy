package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;

@Repository
@Mapper
public interface NoticeDAO {

	// 댓글 등록
	int insertComment(BoardVO vo);
	
	// 댓글 리스트 가져오기
	List<BoardVO> selectComments(int parent);
	
	// 댓글 한건 가져오기
	BoardVO selectComment(int no);
	
	// 댓글 삭제
	int deleteNotice(int no);
	
	// 댓글 수정
	int modifyComment(BoardVO vo);
	
	
}
