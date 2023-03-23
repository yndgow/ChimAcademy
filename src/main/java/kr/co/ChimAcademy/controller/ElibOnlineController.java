package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ElibOnlineController {
	
	@GetMapping("elib/online/reading")
	public String reading() {
		return "elib/online/reading";
	};
	
	@GetMapping("elib/online/magazine")
	public String magazine() {
		return "elib/online/magazine";
	};
	
	@GetMapping("elib/online/humanArt")
	public String humanArt() {
		return "elib/online/humanArt";
	};
	
	@GetMapping("elib/online/DBpia")
	public String DBpia() {
		return "elib/online/DBpia";
	};
	
	@GetMapping("elib/online/E_learning")
	public String E_learning() {
		return "elib/online/E_learning";
	};
	
	@GetMapping("elib/online/busanPost")
	public String busanPost() {
		return "elib/online/busanPost";
	};
	
}
