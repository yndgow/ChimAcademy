package kr.co.ChimAcademy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ChimAcademy.dao.ChineseBoardDAO;
import kr.co.ChimAcademy.vo.BoardVO;

@Service
public class ChineseBoardService {

	@Autowired
	private ChineseBoardDAO dao;

	public void insertBoard(BoardVO vo) {
		dao.insertBoard(vo);
	};

	@Transactional
	public BoardVO selectBoard(int no) {
		BoardVO vo = dao.selectBoard(no);
		dao.boardHitsUpdate(no);
		return vo;
	};

	@Transactional
	public List<BoardVO> selectBoards(int start) {
	 	List<BoardVO> boards = dao.selectBoards(start);
	 	
	 	for(BoardVO board : boards) {
	 	int commentsCount = dao.selectCountComments(board.getNo());
	 	board.setCommentsCount(commentsCount);
	 	}
		
		return boards;
	};

	public void updateBoard(BoardVO vo) {
		dao.updateBoard(vo);
	};

	public void deleteBoard(int no) {
		dao.deleteBoard(no);
	};
	
	public void deleteComments(int no) {
		dao.deleteComments(no);
	}
	
	
	
	/* 댓글쓰기 */
	@Transactional
	public BoardVO insertComment(BoardVO vo) {
		dao.insertComment(vo);
		return dao.selectComment();
	};

	/* 페이징 */
	public int selectCountTotal() {
		return dao.selectCountTotal();
	};

	// 현재 페이지 번호
	public int getCurrentPage(String pg) {
		int currentPage = 1;

		if (pg != null) {
			currentPage = Integer.parseInt(pg);
		}

		return currentPage;
	}

	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage - 1) * 10;
	}

	// 마지막 페이지 번호
	public int getLastPageNum(int total) {

		int lastPageNum = 0;

		if (total % 10 == 0) {
			lastPageNum = total / 10;
		} else {
			lastPageNum = total / 10 + 1;
		}
		return lastPageNum;
	}

	// 페이지 시작 번호
	public int getPageStartNum(int total, int start) {
		return total - start;
	}

	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {

		int groupCurrent = (int) Math.ceil(currentPage / 10.0);
		int groupStart = (groupCurrent - 1) * 10 + 1;
		int groupEnd = groupCurrent * 10;

		if (groupEnd > lastPageNum) {
			groupEnd = lastPageNum;
		}

		int[] groups = { groupStart, groupEnd };

		return groups;
	}

}
