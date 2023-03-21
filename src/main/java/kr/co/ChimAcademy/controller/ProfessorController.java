package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfessorController {

	@GetMapping("professor/credit")
	public String credit() {
		return "professor/credit";
	}
	
	@GetMapping("professor/eval")
	public String eval() {
		return "professor/eval";
	}
	
	@GetMapping("professor/manage")
	public String manage() {
		return "professor/manage";
	}
	
	@GetMapping("professor/my")
	public String mypage() {
		return "mypage/professor/my";
	}
	
	@GetMapping("professor/my/modify")
	public String mypagemodify() {
		return "mypage/professor/modify";
	}

	
}
