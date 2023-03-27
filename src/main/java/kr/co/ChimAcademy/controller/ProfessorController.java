package kr.co.ChimAcademy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
		
		MemberVO vo = service.selectProMy(uid);
		List<MemberVO> lecture = service.selectProlecture(uid);
		
		// 강의시간 출력(time)
		for(MemberVO lec : lecture) {
			String begin = lec.getBeginTime();
			String end = lec.getEndTime();
			
			int beginT = Integer.parseInt(begin);
			int endT = Integer.parseInt(end);
			
			String time = "";
						
			for(int i = beginT; i<=endT; i++) {
				time += i ;
				
			}
			if(time == "1"){
				lec.setBeginTime("");
			}else {
				lec.setBeginTime(time);
			}

		}
		model.addAttribute("uid", uid);
		model.addAttribute("professor", vo);
		model.addAttribute("professorlec", lecture);
		
		return "mypage/professor/my";
	}
	
	@GetMapping("professor/my/modify")
	public String mypagemodify(@AuthenticationPrincipal MyUserDetails member, Model model) {
		MemberEntity mem = member.getUser();
		MemberVO vo = service.selectProMy(mem.getUid());
		
		model.addAttribute("professor", vo);
		model.addAttribute("member", mem);
				
		return "mypage/professor/modify";
	}
	
	// 상세정보 업데이트
	@PostMapping("professor/my/modify")
	public String mypagemodify(MemberVO vo, @AuthenticationPrincipal MyUserDetails member) {
		MemberEntity mem = member.getUser();
		String uid = mem.getUid();
		
		service.updateProMy(vo);
		
		String career = vo.getCareer();
		service.updateProMyinfo(vo);
		
		return "redirect:/professor/my/modify";
	}
	
	// 이미지 업데이트
		@PostMapping("professor/my/modifyProfile")
		public String insertProfile(@AuthenticationPrincipal MyUserDetails member, MemberVO vo) {
			
			String nName = service.updateProfile(vo);
			member.getUser().setProfile(nName);
			return "redirect:/professor/my/modify";
		}
}
