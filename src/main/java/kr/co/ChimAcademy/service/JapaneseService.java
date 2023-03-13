package kr.co.ChimAcademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.JapaneseDAO;
import kr.co.ChimAcademy.vo.BoardVO;

@Service
public class JapaneseService {
	
	@Autowired
	private JapaneseDAO dao;
	
	public List<BoardVO> selectJapanese(){
		return dao.selectJapanese();
	}
	
	public int insertJapanese(BoardVO vo) {
		return dao.insertJapanese(vo);
	}

}
