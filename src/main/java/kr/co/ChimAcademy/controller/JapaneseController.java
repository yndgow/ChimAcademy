package kr.co.ChimAcademy.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.JapaneseService;
import kr.co.ChimAcademy.vo.BoardVO;
import kr.co.ChimAcademy.vo.MemberVO;

@Controller
public class JapaneseController {
	
	@Autowired
	private JapaneseService service;

	@GetMapping("board/P701/list")
	public String list(Model model) {
		
		List<BoardVO> vo = service.selectJapanese();
		model.addAttribute("vo", vo);
		return "board/P701/list";
	}
	
	@GetMapping("board/P701/modify")
	public String modify() {
		return "board/P701/modify";
	}
	
	@GetMapping("board/P701/view")
	public String view() {
		return "board/P701/view";
	}
	
	@GetMapping("board/P701/write")
	public String write(@AuthenticationPrincipal MyUserDetails member, Model model) {
		MemberEntity mem = member.getUser();
		model.addAttribute("member", mem);
		return "board/P701/write";
	}
	
	@PostMapping("board/P701/write")
	public String write(BoardVO vo, HttpServletRequest req) {
		
		int result = service.insertJapanese(vo);

		
		return "redirect:/board/P701/list";
	}

}
