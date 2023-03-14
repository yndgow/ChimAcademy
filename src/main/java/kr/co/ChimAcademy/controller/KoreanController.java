package kr.co.ChimAcademy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.board.KoreanService;
import kr.co.ChimAcademy.vo.BoardVO;

@Controller
public class KoreanController {
	/* A101 국어국문학과 작업용 컨트롤러 - 김훈 20230313*/
	
	@Autowired
	private KoreanService service;
	
	@GetMapping("board/A101/list")
	public String A101_list(Model model) {
		
		List<BoardVO> vo = service.selectBoards();
		model.addAttribute("Korean", vo);
		return "board/A101/list";
	}
	
	@GetMapping("board/A101/modify")
	public String A101_modify() {
		return "board/A101/modify";
	}
	
	@GetMapping("board/A101/view")
	public String A101_view() {
		return "board/A101/view";
	}
	/* 글 작성 */
	@GetMapping("board/A101/write")
	public String A101_write(@AuthenticationPrincipal MyUserDetails member, Model model) {
		MemberEntity mem = member.getUser();
		model.addAttribute("member", mem);
		return "board/A101/write";
	}
	
	@PostMapping("board/A101/write")
	public String A101_write(BoardVO vo, HttpServletRequest request) {
		int result = service.insertBoard(vo);
		return "redirect:board/A101/list";
	}
}
