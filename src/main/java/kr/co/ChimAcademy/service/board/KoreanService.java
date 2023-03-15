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
	public List<BoardVO> selectBoards(int start) {
		return dao.selectBoards(start);
	}
	
	// 국문학과 게시판 보기(View)
	public BoardVO selectBoard(int no) {
		return dao.selectBoard(no);
	}
	
	// 국문학과 게시판 작성(Write)
	public int insertBoard(BoardVO vo) {
		int result = dao.insertBoard(vo);
		return result;
	}
	
	// 국문학과 게시판글 삭제(Delete)
	public int deleteBoard(int no) {
		return dao.deleteBoard(no);
	}
	
	/* 국문학과 게시판 페이징 */
	// 페이징
	public int selectCount() {
		return dao.selectCount();
	}
	
	// 현재 페이지 번호
	public int getCurrnetPage(String pg) {
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	
	// 페이시 시작
	public int getLimitStrat(int currentPage) {
		return (currentPage - 1) * 10;
	}
	
	// Last Page Number
	public int getLastPageNum(int total) {
		int lastPageNum = 0;
		if(total % 10 == 0) {
			lastPageNum = total / 10;
		}else {
			lastPageNum = total / 10+1;
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
		int groupStart = (groupCurrent - 1) * 10+1;
		int groupEnd = groupCurrent * 10;
		
		if(groupEnd > lastPageNum) {
			groupEnd = lastPageNum;
		}
		int[] groups = {groupStart, groupEnd};
		return groups;
	}
}
