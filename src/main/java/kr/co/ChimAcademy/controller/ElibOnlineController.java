package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ElibOnlineController {
	
	@GetMapping("elib/online/reading")
	public String reading(Model model) {
		int _lnb = 1;
		model.addAttribute("_lnb",_lnb);
		return "elib/online/reading";
	};
	
	@GetMapping("elib/online/magazine")
	public String magazine(Model model) {
		int _lnb = 2;
		model.addAttribute("_lnb",_lnb);
		return "elib/online/magazine";
	};
	
	@GetMapping("elib/online/humanArt")
	public String humanArt(Model model) {
		int _lnb = 3;
		model.addAttribute("_lnb",_lnb);
		return "elib/online/humanArt";
	};
	
	@GetMapping("elib/online/DBpia")
	public String DBpia(Model model) {
		int _lnb = 4;
		model.addAttribute("_lnb",_lnb);
		return "elib/online/DBpia";
	};
	
	@GetMapping("elib/online/E_learning")
	public String E_learning(Model model) {
		int _lnb = 5;
		model.addAttribute("_lnb",_lnb);
		return "elib/online/E_learning";
	};
	
	@GetMapping("elib/online/busanPost")
	public String busanPost(Model model) {
		int _lnb = 6;
		model.addAttribute("_lnb",_lnb);
		return "elib/online/busanPost";
	};
	
	@GetMapping("elib/online/gudok")
	public String gudok() {
		return "elib/online/gudok";
	};
}
