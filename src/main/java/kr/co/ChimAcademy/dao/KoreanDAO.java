package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;

@Repository
@Mapper
public interface KoreanDAO {
	// 게시물 리스트 출력
	public List<BoardVO> selectBoards();
	
	// 게시글 작성
	public int insertBoard(BoardVO vo);
}
