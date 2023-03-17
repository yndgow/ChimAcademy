package kr.co.ChimAcademy.controller;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.repository.BoardRepo;
import kr.co.ChimAcademy.service.NoticeService;


@Controller
public class AcademyBoardController {

	private final NoticeService service;
	private final BoardRepo repo;
	
	public AcademyBoardController(NoticeService service, BoardRepo repo) {
		this.service = service;
		this.repo = repo;
	}


	// Mabatis
//	@GetMapping("notice")
//	public String list(Model model, @RequestParam(defaultValue = "1")int pg) {
//		
//		int total = service.countNotice();
//		
//		int currentPage = service.getCurrentPage(pg);
//		int start = service.getLimitStart(currentPage);
//		int lastPageNum = service.getLastPageNum(total);
//		int pageStartNum = service.getPageStartNum(total, start);
//		int groups[] = service.getPageGroup(currentPage, lastPageNum);
//		
//		model.addAttribute("currentPg", currentPage);
//		model.addAttribute("lastPageNum", lastPageNum);
//		model.addAttribute("pageStartNum", pageStartNum);
//		model.addAttribute("startPages", groups[0]);
//		model.addAttribute("endPages", groups[1]);
//		model.addAttribute("list", service.list(start));
//		
//		
//		return "notice/list";
//	}
	// JPA
	@GetMapping("notice")
	public String list(Model model, @RequestParam(defaultValue = "1")int pg) {

		Page<BoardEntity> page = service.list2(pg);
		int[] pageNumbers = service.getPageNumbers(page);
		
		model.addAttribute("startPages", pageNumbers[0]);
		model.addAttribute("endPages", pageNumbers[1]);
		model.addAttribute("list", page.getContent());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("currentPg", pg);
		model.addAttribute("totalPosts", page.getTotalElements());
		
		
		return "notice/list";
	}
	@GetMapping("notice/view")
	public String view(Model m, int no) {
		BoardEntity entity = repo.findById(no).orElse(null);
		if(entity != null) {
			entity.getMemberEntity();
		}
		m.addAttribute("view", entity);
		return "notice/view";
	}
	
	
	@GetMapping("notice/write")
	public String write() {
		return "notice/write";
	}
	
	@GetMapping("notice/modify")
	public String modify(Model m, int no) {
		m.addAttribute("view", repo.findById(no));
		return "notice/modify";
	}
	
	
	
	
	
	
	@GetMapping("notice/delete")
	public String delete(Model m, int no) {
		m.addAttribute("view", repo.findById(no));
		return "notice/delete";
	}
	
}
