package kr.co.GreenAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
	
	@GetMapping("student/class/signup")
	public String signUp() {
		return "student/signUp";
	}
}
