package kr.co.ChimAcademy.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {
	
	@GetMapping(value = {"/", "/index"})
	public String index(Model model, @CookieValue(name = "remember-me", required = false) String remebermeCookie) {
//		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(remebermeCookie!= null) {
//		if(authentication!= null && authentication.isAuthenticated()) {
			return "redirect:/notice";
		}else {
			System.out.println("로그인페이지로");
			return "member/login";
		}
		
	}
}
