package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
	
	@GetMapping("student/class/signup")
	public String signUp() {
		return "student/signUp";
	}
	
	@GetMapping("student/class/timeTable")
	public String timeTable() {
		return "student/timeTable";
	}
	
	@GetMapping("student/class/eval")
	public String eval() {
		return "student/eval";
	}
	
	@GetMapping("student/my")
	public String mypage() {
		return "mypage/student/my";
	}
	
	@GetMapping("student/my/modify")
	public String mypagemodify() {
		return "mypage/student/modify";
	}
}
