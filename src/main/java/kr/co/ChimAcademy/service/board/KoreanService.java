package kr.co.ChimAcademy.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.KoreanDAO;
import kr.co.ChimAcademy.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KoreanService {
	@Autowired
	private KoreanDAO dao;
	
	// 게시물 리스트 출력
	public List<BoardVO> selectBoards() {
		return dao.selectBoards();
	}
	
	// 게시글 작성
	public int insertBoard(BoardVO vo) {
		int result = 0;
		return result;
	}
}
