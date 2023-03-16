package kr.co.ChimAcademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.service.ChineseBoardService;
import kr.co.ChimAcademy.vo.BoardVO;


@Controller
public class ChineseController {
	
	@Autowired
	private ChineseBoardService service;
	
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
		return "redirect:/board/D107/list";
	}
	@GetMapping("board/D107/view")
	public String D107_view(Model model, int no) {
//		BoardVO rdfgh5rys= service.selectBoard(no);
		model.addAttribute("board", service.selectBoard(no));
		return "board/D107/view";
	}
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
