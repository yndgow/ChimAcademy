package kr.co.ChimAcademy.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.Lec_SugangEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.SignUpService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SignUpController {
	
	private final SignUpService signUpService;

	public SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	// 학생 수강신청 페이지
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
		
		// 수강 신청 내역
		List<LecSugangDto> sugangs = signUpService.getSugangs(member.getUid());
		
		model.addAttribute("member", member);
		model.addAttribute("year", year);
		model.addAttribute("semester", semester);
		model.addAttribute("depName", depName);
		model.addAttribute("majorName", majorName);
		model.addAttribute("departments", departments);
		model.addAttribute("sugangs", sugangs);
		
		return "student/signUp";
	}
	
	// 전공 찾기
	@ResponseBody
	@GetMapping("lecture/{depCode}")
	public List<MajorEntity> getMajors(@PathVariable String depCode){
		return signUpService.getMajors(depCode);
	}
	
	// 수강신청 
	@ResponseBody
	@PostMapping("student/class/signup")
	public Map<String, Integer> insertSugang(Lec_SugangEntity entity) {
		log.info("entity : " + entity);

		// 반환할 map 생성
		Map<String, Integer> json = new HashMap<>();
		
		
		// 조건 체크(기신청여부, 인원)
		boolean chk = signUpService.checkSugang(entity);
		if(chk) {
			// 수강 신청 진행
			signUpService.insertSugang(entity);
			
			json.put("result", 1);
			return json;
		}else {
			// 수강 불가
			json.put("result", 0);
			return json;
		}
		
	}
	
}
