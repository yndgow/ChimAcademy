package kr.co.ChimAcademy.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;
import kr.co.ChimAcademy.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@ResponseBody
	@GetMapping("member/search")
	public String searchUid(String name, String birth, String hp) {
		MemberUidMapping entity = memberService.searchUid(name, birth, hp);
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		log.info(json);
		return json;
	}
	
	@GetMapping("member/join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("member/join")
	public String memberJoin(MemberEntity entity, HttpServletRequest req) {
		log.info("mem: " + entity);
		memberService.updateMember(entity, req);
		return "redirect:/index";
	}

	@GetMapping("member/confirm")
	public String confirmPage() {
		return "member/confirm";
	}
	
	@ResponseBody
	@GetMapping("member/email")
	public String emailAuth(String addr) {
		int code = 0;
		try {
			code = memberService.sendEmail(addr);
		} catch (MessagingException e) {
			log.error(e.getMessage());
		}
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.writeValueAsString(code);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		
		
		return json;
	}
}
