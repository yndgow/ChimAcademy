package kr.co.ChimAcademy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.StudentService;
import kr.co.ChimAcademy.vo.MemberVO;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	@GetMapping("student/class/signup2")
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
	public String mypage(Principal pricipal, Model model) {
		
		String uid = pricipal.getName();
		MemberVO vo = service.selectStudent(uid);
		List<MemberVO> lecture = service.selectLectures(uid);
		
		model.addAttribute("vo", vo);
		model.addAttribute("lecture", lecture);
		return "mypage/student/my";
	}
	
	@GetMapping("student/my/modify")
	public String mypagemodify() {
		return "mypage/student/modify";
	}

}
