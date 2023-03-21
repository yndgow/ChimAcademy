package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ElibOnlineController {
	
	@GetMapping("elib/online/reading")
	public String reading() {
		return "elib/online/reading";
	};
	
}
