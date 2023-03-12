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
	@GetMapping("elib/info/list")
	public String noticeList() {
		return "elib/info/list";
	}
	@GetMapping("elib/info/write")
	public String noticeWrite() {
		return "elib/info/write";
	}
	@GetMapping("elib/info/modify")
	public String noticeModify() {
		return "elib/info/modify";
	}
	@GetMapping("elib/info/view")
	public String noticeView() {
		return "elib/info/view";
	}
	@GetMapping("elib/info/how")
	public String how() {
		return "elib/info/how";
	}
	@GetMapping("elib/info/install")
	public String install() {
		return "elib/info/install";
	}
	@GetMapping("elib/mylibrary/mylib")
	public String mylib() {
		return "elib/mylibrary/mylib";
	}
}
