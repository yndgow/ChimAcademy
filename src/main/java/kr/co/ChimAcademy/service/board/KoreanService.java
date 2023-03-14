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
	
	// 국문학과 게시판 목록 출력(List)
	public List<BoardVO> selectBoards() {
		return dao.selectBoards();
	}
	
	// 국문학과 게시판 작성 출력(Write)
	public int insertBoard(BoardVO vo) {
<<<<<<< Updated upstream
		int result = 0;
		
=======
		int result = dao.insertBoard(vo);
>>>>>>> Stashed changes
		return result;
	}
	
	// 국문학과 게시판 보기(view)
	public BoardVO selectBoard(int no) {
		return dao.selectBoard(no);
	}
	
}
