package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;


@Repository
@Mapper
public interface ChineseBoardDAO {
	
	public void insertBoard(BoardVO vo);
	public BoardVO selectBoard(int no);
	public List<BoardVO> selectBoards(int start);
	public void updateBoard(BoardVO vo);
	public void deleteBoard(int no);
	public void deleteComments(int no);
	public int selectCountTotal();
	public int boardHitsUpdate(int no);
	public int insertComment(BoardVO vo);
	public BoardVO selectComment();
	public int selectCountComments(int parent);
}
