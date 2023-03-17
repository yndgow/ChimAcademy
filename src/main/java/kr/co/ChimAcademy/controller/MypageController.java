package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
	
	@GetMapping("mypage/student/modify")
	public String modifyStudent() {
		return "mypage/student/modify";
	}
	
	@GetMapping("mypage/professor/modify")
	public String modifyProfessor() {
		return "mypage/professor/modify";
	}
}
