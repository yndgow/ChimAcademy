package kr.co.ChimAcademy.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ChimAcademy.config.MyUserDetails;


@Controller
public class MainController {
	
	@GetMapping(value = {"/", "/index"})
	public String index(Model model, @AuthenticationPrincipal MyUserDetails member) {
		if(member != null) {
			model.addAttribute("member", member.getUser());
			return "notice/list";
		}else {
			return "member/login";
		}
		
	}
}
