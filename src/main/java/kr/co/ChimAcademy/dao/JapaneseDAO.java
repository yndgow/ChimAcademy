package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;

@Repository
@Mapper
public interface JapaneseDAO {

	public List<BoardVO> selectJapaneses(int start);
	public BoardVO selectJapanese(int no);
	
	public int insertJapanese(BoardVO vo);
	public int updateJapanese(BoardVO vo);
	public int hitJapanese(BoardVO vo);
	
	public int deleteJapanese(int no);
	
	//댓글작성
	public int insertComment(BoardVO vo);
	
	//댓글수정
	public int updateComment(BoardVO vo);

	//댓글보기
	public List<BoardVO> selectComment(int parent);
	
	//댓글 카운트
	public int countComment(int no);
	
	
	//페이징 작업
	public int selectCountTotal();
}
