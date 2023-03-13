package kr.co.ChimAcademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	@GetMapping("board/A101/write")
	public String A101_write() {
		return "board/A101/write";
	}
}
