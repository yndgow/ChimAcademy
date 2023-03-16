package kr.co.ChimAcademy.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.AssistantService;
import kr.co.ChimAcademy.service.MemberService;
import kr.co.ChimAcademy.vo.DepartmentVO;
import kr.co.ChimAcademy.vo.LectureVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AssistantController {

	private final AssistantService assistantService;
	private final MemberService memberService;
	
	public AssistantController(AssistantService assistantService, MemberService memberService) {
		this.assistantService = assistantService;
		this.memberService = memberService;
	}

	// 인원 추가페이지 이동
	@GetMapping("assistant/insert")
	public String insert(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		
		MemberEntity member = userDetails.getUser();
		DepartmentVO vo = assistantService.selectDep(member.getUid());
		
		model.addAttribute("department", vo);
		model.addAttribute("member", member);
		
		return "assistant/insert";
	}
	
	// 인원 추가 여러건
	@ResponseBody
	@PostMapping("assistant/insert")
	public List<MemberEntity> insertMembers(@RequestBody List<MemberEntity> members) {
		return assistantService.insertMembers(members);
	}
	
	
	
	@GetMapping("assistant/manage")
	public String manage() {
		return "assistant/manage";
	}
	
	@GetMapping("assistant/modify")
	public String modify() {
		return "assistant/modify";
	}
	
	// 강의 관리 
	@GetMapping("assistant/lecuture")
	public String lecuture(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		DepartmentVO vo = assistantService.selectDep(userDetails.getUser().getUid());
		List<MajorEntity> list = assistantService.selectMajors(vo.getDepCode());
		model.addAttribute("department", vo);
		model.addAttribute("majors", list);
		return "assistant/lecuture";
	}

	@ResponseBody
	@GetMapping("assistant/lecture/search")
	public List<LectureVO> selectLectures(LectureVO vo){
		
		List<LectureVO> lectures = assistantService.selectLectures(vo);
		
		return lectures;
	}
	
}
