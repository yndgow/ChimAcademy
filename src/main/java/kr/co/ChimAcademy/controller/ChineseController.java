package kr.co.ChimAcademy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.service.ChineseBoardService;
import kr.co.ChimAcademy.service.NoticeService;
import kr.co.ChimAcademy.vo.BoardVO;


@Controller
public class ChineseController {
	
	@Autowired
	private ChineseBoardService service;
	
	@Autowired
	private NoticeService Nservice;
	
	@GetMapping("board/D107/list")
	public String D107_list(Model model, @RequestParam(defaultValue = "1") String pg) {
		
		/* 페이징 */
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.selectCountTotal();
		int lastPageNum = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<BoardVO> vo = service.selectBoards(start);
		
		model.addAttribute("boards", vo);
		model.addAttribute("pg", pg);
		model.addAttribute("start", start);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		return "board/D107/list";
	}
	@GetMapping("board/D107/modify")
	public String D107_modify_get(Model model, int no) {
		BoardVO board = service.selectBoard(no);
		model.addAttribute("board", board);
		return "board/D107/modify";
	}
	@PostMapping("board/D107/modify")
	public String D107_modify_post(BoardVO vo) {
		service.updateBoard(vo);
		return "redirect:/board/D107/view?no="+vo.getNo();
	}
	@GetMapping("board/D107/delete")
	public String D107_delete(int no) {
		service.deleteBoard(no);
		service.deleteComments(no);
		return "redirect:/board/D107/list";
	}
	@GetMapping("board/D107/view")
	public String D107_view(@AuthenticationPrincipal MyUserDetails member, Model model, int no) {
		model.addAttribute("member", member.getUser());
		model.addAttribute("board", service.selectBoard(no));
		// 댓글 가져오기
		List<BoardVO> comments = Nservice.selectComments(no);
		model.addAttribute("comments", comments);
		return "board/D107/view";
	}
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="board/D107/commentWrite", method =
	 * {RequestMethod.POST}) public Map<String, BoardVO> commentWrite(BoardVO vo) {
	 * BoardVO comment = service.insertComment(vo); Map<String, BoardVO> map = new
	 * HashMap<>(); map.put("comment", comment); return map; }
	 */
	
	@GetMapping("board/D107/write")
	public String D107_write_get(@AuthenticationPrincipal MyUserDetails member, Model model, BoardVO vo) {
		model.addAttribute("member", member.getUser());
		return "board/D107/write";
	}
	
	@PostMapping("board/D107/write")
	public String D107_write_post(BoardVO vo) {
		service.insertBoard(vo);
		return "redirect:/board/D107/list";
	}
	

	
	
	
}
