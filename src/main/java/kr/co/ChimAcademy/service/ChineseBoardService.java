package kr.co.ChimAcademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.ChineseBoardDAO;
import kr.co.ChimAcademy.vo.BoardVO;

@Service
public class ChineseBoardService {
	
	@Autowired
	private ChineseBoardDAO dao;
	
	public void insertBoard(BoardVO vo) {
		dao.insertBoard(vo);
	};
	public BoardVO selectBoard(int no) {
		return dao.selectBoard(no);
	};
	public List<BoardVO> selectBoards(){
		return dao.selectBoards();
	};
	public void updateBoard(BoardVO vo) {
		dao.updateBoard(vo);
	};
	public void deleteBoard(int no) {
		dao.deleteBoard(no);
	};
	public int selectCountTotal() {
		return dao.selectCountTotal();
	};

}
