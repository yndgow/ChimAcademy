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
	
	public List<BoardVO> selectJapaneses(int start){
		return dao.selectJapaneses(start);
	}
	
	public BoardVO selectJapanese(int no){
		return dao.selectJapanese(no);
	}
	
	public int insertJapanese(BoardVO vo) {
		return dao.insertJapanese(vo);
	}
	
	public int updateJapanese(BoardVO vo) {
		return dao.updateJapanese(vo);
	};

	
	public int deleteJapanese(int no) {
		return dao.deleteJapanese(no);
	};
	
	//조회수 증가
	public int hitJapanese(BoardVO vo) {
		return dao.hitJapanese(vo);
	};
	
	//댓글쓰기
	public int insertComment(BoardVO vo) {
		return dao.insertComment(vo);
	}
	
	//댓글수정
	public int updateComment(BoardVO vo) {
		return dao.updateComment(vo);
	}
	
	//댓글보기
	public List<BoardVO> selectComment(int parent){
		return dao.selectComment(parent);
	}
	
	//댓글카운트
	public int countComment(int no) {
		return dao.countComment(no);
	}
	

	//페이징 작업
	public int selectCountTotal() {
		return dao.selectCountTotal();
	};
	
	// 현재 페이지 번호
		public int getCurrentPage(String pg) {
			int currentPage = 1;
			
			if(pg != null) {
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
			
			if(total % 10 == 0) {
				lastPageNum = total / 10;			
			}else {
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
				
				if(groupEnd > lastPageNum) {
					groupEnd = lastPageNum;
				}
				
				int[] groups = {groupStart, groupEnd};
				
				return groups;
			}

}
