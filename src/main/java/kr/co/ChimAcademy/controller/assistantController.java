package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class assistantController {

	@GetMapping("assistant/insert")
	public String insert() {
		return "assistant/insert";
	}
	
	@GetMapping("assistant/manage")
	public String manage() {
		return "assistant/manage";
	}
	
	@GetMapping("assistant/modify")
	public String modify() {
		return "assistant/modify";
	}
	
	@GetMapping("assistant/myAssistant")
	public String myAssistant() {
		return "assistant/myAssistant";
	}
	

	
}
