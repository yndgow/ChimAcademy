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

	// 아이디 찾기
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
	
	// 가입페이지 이동
	@GetMapping("member/join")
	public String join() {
		return "member/join";
	}
	
	// 가입하기
	@PostMapping("member/join")
	public String memberJoin(MemberEntity entity, HttpServletRequest req) {
		log.info("mem: " + entity);
		memberService.updateMember(entity, req);
		return "redirect:/index";
	}

	// 아이디 찾기 페이지 이동
	@GetMapping("member/confirm")
	public String confirmPage() {
		return "member/confirm";
	}
	
	// 이메일 중복 확인
	@ResponseBody
	@GetMapping("member/email")
	public String countEmail(String email) {
		int result = memberService.countEmail(email);
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		
		log.info("result : " + result);
		return json;
	}
	
	// 이메일 전송
	@ResponseBody
	@GetMapping("member/emailCode")
	public String emailAuth(String email) {
		int code = 0;
		try {
			code = memberService.sendEmail(email);
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
	
	// 비밀번호 찾기 페이지 이동
	@GetMapping("member/findPw")
	public String findPw() {
		return "member/findPw";
	}
}
