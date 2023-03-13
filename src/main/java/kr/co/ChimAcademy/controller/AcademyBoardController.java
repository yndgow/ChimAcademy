package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcademyBoardController {

	@GetMapping("notice")
	public String notice() {
		return "notice/list";
	}
}
