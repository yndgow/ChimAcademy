package kr.co.ChimAcademy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.AssistantDAO;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.repository.BoardRepo;
import kr.co.ChimAcademy.vo.BoardVO;

@Service
public class NoticeService {
	
	private final BoardRepo repo;
	private final AssistantDAO dao;

	public NoticeService(BoardRepo repo, AssistantDAO dao) {
		this.repo = repo;
		this.dao = dao;
	}
	
	
	public Page<BoardEntity> list2(int pg){
		Pageable pageable = PageRequest.of(pg-1, 10, Sort.by("rdate").descending());
		return repo.findByLecCodeNotNull(pageable);
	}
	
	public List<BoardVO> list(int start){
		return dao.selectNotices(start);
	}
	
	public int countNotice() {
		return dao.countNotice();
	}
	
	
	
	
	
	// 현재 페이지 번호
	public int getCurrentPage(int pg) {
		int currentPage = 1;
		if(pg != 0){
			currentPage = pg;
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
	
	// 페이징에 필요한 start, endpage 가져오기
	public int[] getPageNumbers(Page<?> page) {
	    int totalPages = page.getTotalPages();
	    int currentPage = page.getNumber() + 1;

	    int startPage = ((currentPage - 1) / 10) * 10 + 1;
	    int endPage = Math.min(startPage + 9, totalPages);

	    int[] pageNumbers = {startPage, endPage};
	    return pageNumbers;
	}

	
}
