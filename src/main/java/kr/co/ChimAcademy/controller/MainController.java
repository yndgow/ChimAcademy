package kr.co.ChimAcademy.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
	
	@GetMapping(value = {"/", "/index"})
	public String index(Model model, @CookieValue(name = "remember-me", required = false) String remebermeCookie, Principal principal) {
		if(remebermeCookie!= null || principal != null) {
			return "redirect:/notice";
		}else {
			System.out.println("로그인페이지로");
			return "member/login";
		}
		
	}
}
