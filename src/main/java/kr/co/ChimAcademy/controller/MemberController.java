package kr.co.ChimAcademy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;
import kr.co.ChimAcademy.service.MemberService;

@Controller
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@ResponseBody
	@GetMapping("member/search")
	public ResponseEntity<String> searchUid(String name, String birth, String hp) {
		MemberUidMapping entity = memberService.searchUid(name, birth, hp);
		
		ObjectMapper om = new ObjectMapper();
		String json = null;
		try {
			json = om.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpStatus status = null;
		if(entity != null) {
			status = HttpStatus.OK;
		}else {
			status = HttpStatus.BAD_REQUEST;
		}
		
		ResponseEntity<String> response = new ResponseEntity<>(json, status);
		return response;
	}
	
	@GetMapping("member/join")
	public String join() {
		return "member/join";
	}

	@GetMapping("member/confirm")
	public String confirmPage() {
		return "member/confirm";
	}
}
