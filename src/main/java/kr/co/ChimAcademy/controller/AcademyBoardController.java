package kr.co.ChimAcademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ChimAcademy.config.MyUserDetails;

@Controller
public class AcademyBoardController {

	@GetMapping("notice")
	public String notice(@AuthenticationPrincipal MyUserDetails member, Model model) {
		model.addAttribute("member", member.getUser());
		return "notice/list";
	}
}
