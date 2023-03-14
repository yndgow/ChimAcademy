package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;

@Repository
@Mapper
public interface KoreanDAO {
	// 국문학과 게시판 목록 출력(List)
	public List<BoardVO> selectBoards();
	
	// 국문학과 게시판 작성 출력(Write)
	public int insertBoard(BoardVO vo);
	
	// 국문학과 게시판 보기(view)
	public BoardVO selectBoard(int no);
}
