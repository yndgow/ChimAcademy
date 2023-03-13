package kr.co.ChimAcademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ChimAcademy.service.ChineseBoardService;
import kr.co.ChimAcademy.vo.BoardVO;


@Controller
public class ChineseController {
	
	@Autowired
	private ChineseBoardService service;
	
	@GetMapping("board/D107/list")
	public String D107_list(Model model) {
		List<BoardVO> boards = service.selectBoards();
		model.addAttribute("boards", boards);
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
