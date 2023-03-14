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

	public int deleteJapanese(int no);
	
	
	//페이징 작업
	public int selectCountTotal();
}
