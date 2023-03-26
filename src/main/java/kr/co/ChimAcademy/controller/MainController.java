package kr.co.ChimAcademy.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
	// 로그인 확인
	@GetMapping(value = {"/", "/index", "member/login"})
	public String index(Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}else {
			return "redirect:/notice";
		}
	}

	// 로그인 페이지
	@GetMapping(value={"login"})
	public String login() {
		return "member/login";
	}
	
	// 접근불가페이지
	@GetMapping("accessDenied")
	public String denied() {
		return "denied";
	}

}
