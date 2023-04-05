package kr.co.ChimAcademy.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.AssistantDAO;
import kr.co.ChimAcademy.dao.NoticeDAO;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.repository.BoardRepo;
import kr.co.ChimAcademy.util.IpAddress;
import kr.co.ChimAcademy.vo.BoardVO;

@Service
public class NoticeService {
	
	private final BoardRepo repo;
	private final AssistantDAO dao;
	private final IpAddress ip;
	private final NoticeDAO noticeDao;

	public NoticeService(BoardRepo repo, AssistantDAO dao, IpAddress ip, NoticeDAO noticeDao) {
		this.repo = repo;
		this.dao = dao;
		this.ip = ip;
		this.noticeDao = noticeDao;
	}
	
	// 공지사항 글목록(JPA)
	public Page<BoardEntity> list2(int type,int pg){
		Pageable pageable = PageRequest.of(pg-1, 10, Sort.by("rdate").descending());
		return repo.findByTypeAndParent(type, 0, pageable);
	}

	// 공지사항 글목록(Mybatis)
	public List<BoardVO> list(int start){
		return dao.selectNotices(start);
	}
	
	// 공지사항 글 개수
	public int countNotice() {
		return dao.countNotice();
	}
	
	// 공지사항 보기, 수정할 글 가져오기
	public BoardEntity selectNotice(int no) {
		return repo.findById(no).orElse(null);
	}
	
	// 공지사항 쓰기
	public int insertNotice(BoardEntity entity) {
		BoardEntity vo = repo.save(entity);
		return vo.getNo();
	}
	
	// 공지사항 삭제
	public int deleteNotice(int no) {
		int result = noticeDao.deleteNotice(no);
		return result;
		
	}
	
//	public int deleteNotice(int no) {
//		BoardEntity entity = repo.findById(no).orElse(null);
//		int result = 0;
//		if(entity != null) {
//			repo.delete(entity);
//			result = 1;
//		}
//		return result;
//	}
	
	// 공지사항 업데이트
	@Transactional
	public void modifyNotice(BoardEntity entity, HttpServletRequest req) {
		int no = entity.getNo();
		BoardEntity vo = repo.findById(no).orElseThrow();
		vo.setTitle(entity.getTitle());
		vo.setContent(entity.getContent());
		vo.setRegip(ip.getIpAddr(req));
	}
	
	// 조회수 업데이트
	@Transactional
	public void modifyHit(BoardEntity entity) {
		int hit = entity.getHit();
		entity.setHit(++hit);
		repo.save(entity);
	}
	
	// 댓글 등록
	public int insertComment(BoardVO vo) {
		noticeDao.insertComment(vo);
		return vo.getNo();
	};
	
	// 댓글 리스트 가져오기
	public List<BoardVO> selectComments(int parent) {
		return noticeDao.selectComments(parent);
	};
	
	// 댓글 한건 출력
	public BoardVO selectComment(int no) {
		return noticeDao.selectComment(no);
	}
	
	
	// 댓글 수정
	public int modifyComment(BoardVO vo) {
		return noticeDao.modifyComment(vo);
	};
	
	
	
	
	
	
	
	
	// 페이징 필요 영역 //	
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
	
	// 페이징에 필요한 start, endpage 가져오기(JPA)
	public int[] getPageNumbers(Page<?> page) {
	    int totalPages = page.getTotalPages();
	    int currentPage = page.getNumber() + 1;

	    int startPage = ((currentPage - 1) / 10) * 10 + 1;
	    int endPage = Math.min(startPage + 9, totalPages);

	    int[] pageNumbers = {startPage, endPage};
	    return pageNumbers;
	}

	
}
