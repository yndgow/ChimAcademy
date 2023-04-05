package kr.co.ChimAcademy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;

@Repository
@Mapper
public interface KoreanDAO {
	// 국문학과 게시판 목록 출력(List)
	public List<BoardVO> selectBoards(int start);
	
	// 국문학과 게시판 보기(view)
	public BoardVO selectBoard(int no);
	
	// 국문학과 게시판 작성(Write)
	public int insertBoard(BoardVO vo);
	
	// 국문학과 게시판글 삭제(Delete)
	public int deleteBoard(int no);
	
	// 국문학과 게시판글 수정(Update)
	public int updateBoard(BoardVO vo);
	
	// 국문학과 게시판 페이징
	public int selectCount();
	
	// 국문학과 게시판 조회수(Hit)
	public int updateBoardHit(BoardVO vo);
	
	// 국문학과 게시판 댓글
	public int insertBoardComment(BoardVO vo);
	
	// 국문학과 게시판 댓글 출력
	public List<BoardVO> selectBoardComment(int no);
	
	// 국문학과 게시판 댓글 수정
	public int modifyBoardComment(BoardVO vo);
	
	// 국문학과 게시판 댓글 카운트 표시
	public int selectCountComments(int parent);
}
