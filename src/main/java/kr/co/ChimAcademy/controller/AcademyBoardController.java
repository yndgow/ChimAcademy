package kr.co.ChimAcademy.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.service.NoticeService;
import kr.co.ChimAcademy.vo.BoardVO;


@Controller
public class AcademyBoardController {

	private final NoticeService service;
	
	public AcademyBoardController(NoticeService service) {
		this.service = service;
	}

	// JPA 공지사항 리스트
	@GetMapping("notice")
	public String list(Model model, @RequestParam(defaultValue = "1")int pg) {
		// 공지사항은 1
		int type = 1;
		Page<BoardEntity> page = service.list2(type, pg);
		int[] pageNumbers = service.getPageNumbers(page);
		
		model.addAttribute("startPages", pageNumbers[0]);
		model.addAttribute("endPages", pageNumbers[1]);
		model.addAttribute("list", page.getContent());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("currentPg", pg);
		model.addAttribute("totalPosts", page.getTotalElements());
		
		return "notice/list";
	}
	
	// 공지사항 보기
	@GetMapping("notice/view")
	public String view(Model m, int no) {
		// 글 가져오기
		BoardEntity entity = service.selectNotice(no);
		
		// 조회수 업데이트
		service.modifyHit(entity);
		
		// 댓글 가져오기
		List<BoardVO> comments = service.selectComments(no);
		
		m.addAttribute("view", entity);
		m.addAttribute("comments", comments);
		m.addAttribute("commentCount", comments.size());
		
		return "notice/view";
	}
	
	// 공지사항 쓰기 페이지 이동
	@GetMapping("notice/write")
	public String write(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		model.addAttribute("member" , userDetails.getUser());
		return "notice/write";
	}
	
	// 공지사항 쓰기 기능
	@PostMapping("notice/write")
	public String writeNotice(BoardEntity entity) {

		// 쓰고 난 후 그 글의 번호(자동증가된 번호)
		int no = service.insertNotice(entity);
		
		return "redirect:/notice/view?no=" + no;
	}
	
	// 공지사항 수정 페이지 이동
	@GetMapping("notice/modify")
	public String modify(Model m, int no) {
		m.addAttribute("view", service.selectNotice(no));
		return "notice/modify";
	}
	
	// 공지사항 수정 기능
	@PostMapping("notice/modify")
	public String modifyNotice(Model m, BoardEntity entity, HttpServletRequest req) {
		service.modifyNotice(entity, req);
		return "redirect:/notice/view?no="+entity.getNo();
	}
	
	// 공지사항 삭제
	@ResponseBody
	@DeleteMapping("notice/delete/{no}")
	public Map<String, Integer> delete(@PathVariable int no) {
		int result = service.deleteNotice(no);
		Map<String, Integer> json = new HashMap<>();
		json.put("result", result);
		return json;
	}
	
	// 등록 후 등록한 댓글을 db에서 select 후 반환하여 동적으로 댓글에 추가히기
	// 댓글 등록 json으로 반환하기 위해	@ResponseBody 선언
	// 댓글 등록
	@ResponseBody
	@PostMapping("notice/comment/insert")
	public BoardVO insertComment(BoardVO vo) {
		
		// mapper 설정으로 추가된 댓글의 no 값을 가져옴
		int no = service.insertComment(vo);

		// 디비에서 방금 작성한 댓글 가져오기
		BoardVO comment = service.selectComment(no);
		
		// BoardVO 형태로 반환 @ResponseBody 선언으로 json 형태로 변환 후 반환
		return comment;
		
	};
	
	// 댓글 수정
	@ResponseBody
	@PostMapping("notice/comment/modify")
	public Map<String, Integer> modifyComment(BoardVO vo) {
		int result = service.modifyComment(vo);
		Map<String, Integer> json = new HashMap<>();
		json.put("result", result);
		return json;
	};
	
	// Mabatis
//	@GetMapping("notice")
//	public String list(Model model, @RequestParam(defaultValue = "1")int pg) {
//		
//		int total = service.countNotice();
//		
//		int currentPage = service.getCurrentPage(pg);
//		int start = service.getLimitStart(currentPage);
//		int lastPageNum = service.getLastPageNum(total);
//		int pageStartNum = service.getPageStartNum(total, start);
//		int groups[] = service.getPageGroup(currentPage, lastPageNum);
//		
//		model.addAttribute("currentPg", currentPage);
//		model.addAttribute("lastPageNum", lastPageNum);
//		model.addAttribute("pageStartNum", pageStartNum);
//		model.addAttribute("startPages", groups[0]);
//		model.addAttribute("endPages", groups[1]);
//		model.addAttribute("list", service.list(start));
//		
//		
//		return "notice/list";
//	}

	
	
	
	
	
}
