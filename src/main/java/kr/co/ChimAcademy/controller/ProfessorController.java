package kr.co.ChimAcademy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.ProfessorService;
import kr.co.ChimAcademy.vo.MemberVO;

@Controller
public class ProfessorController {
	@Autowired
	private ProfessorService service;
	
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
	public String mypage(Model model, @AuthenticationPrincipal MyUserDetails member) {
		String uid = member.getUser().getUid();
		
		MemberVO vo = service.selectPro(uid);
		
		model.addAttribute("professor", vo);
		model.addAttribute("uid", uid);
		
		return "mypage/professor/my";
	}
	
	@GetMapping("professor/my/modify")
	public String mypagemodify() {
		return "mypage/professor/modify";
	}

	
}
