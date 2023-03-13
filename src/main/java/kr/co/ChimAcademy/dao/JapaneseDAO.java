package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.BoardVO;

@Repository
@Mapper
public interface JapaneseDAO {

	public List<BoardVO> selectJapanese();
	
	public int insertJapanese(BoardVO vo);

}
