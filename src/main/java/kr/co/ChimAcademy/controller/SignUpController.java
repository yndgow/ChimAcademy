package kr.co.ChimAcademy.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.SignUpService;

@Controller
public class SignUpController {
	
	private final SignUpService signUpService;

	public SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	@GetMapping("student/class/signup")
	public String signUp(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		// 연도, 학기
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		int semester = 1;
		if(month >= 8) {
			semester = 2;
		}
		
		// 로그인한 사용자 정보
		MemberEntity member = userDetails.getUser();
		String depName = member.getDepartmentEntity().getDepName();
		String majorName = "-";
		if(member.getMajorEntity() != null) {
			majorName = member.getMajorEntity().getMajorName();
		}
		
		// 모든 학과
		List<DepartmentEntity> departments = signUpService.getDeparments();
		
		model.addAttribute("member", member);
		model.addAttribute("year", year);
		model.addAttribute("semester", semester);
		model.addAttribute("depName", depName);
		model.addAttribute("majorName", majorName);
		model.addAttribute("departments", departments);
		return "student/signUp";
	}
	
	@ResponseBody
	@GetMapping("lecture/{depCode}")
	public List<MajorEntity> getMajors(@PathVariable String depCode){
		return signUpService.getMajors(depCode);
	}
}