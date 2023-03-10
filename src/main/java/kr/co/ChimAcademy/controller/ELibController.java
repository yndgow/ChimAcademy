package kr.co.ChimAcademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ELibController {

	@GetMapping(value = {"elib/","elib/index"})
	public String index() {
		return "elib/index";
	}
	
	@GetMapping("elib/ebook/list")
	public String list() {
		return "elib/ebook/list";
	}
	@GetMapping("elib/ebook/view")
	public String view() {
		return "elib/ebook/view";
	}
	@GetMapping("elib/mylibrary/mylib")
	public String mylib() {
		return "elib/mylibrary/mylib";
	}
}
