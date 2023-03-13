package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping("board/list")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("board/modify")
	public String modify() {
		return "board/modify";
	}
	
	@GetMapping("board/view")
	public String view() {
		return "board/view";
	}
	
	@GetMapping("board/write")
	public String write() {
		return "board/write";
	}
	
	/* D107 중어중문학과 작업용 컨트롤러 - 진윤희 20230313*/
	@GetMapping("board/D107/list")
	public String D107_list() {
		return "board/D107/list";
	}
	@GetMapping("board/D107/modify")
	public String D107_modify() {
		return "board/D107/modify";
	}
	@GetMapping("board/D107/view")
	public String D107_view() {
		return "board/D107/view";
	}
	@GetMapping("board/D107/write")
	public String D107_write() {
		return "board/D107/write";
	}
	
	
}
