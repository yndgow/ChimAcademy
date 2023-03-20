package kr.co.ChimAcademy.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String list(Model model, String pg) {
		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		
		int total = service.selectCountTotal();
		int lastPageNum = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		
		List<BoardVO> vo = service.selectJapaneses(start);
		model.addAttribute("vo", vo);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		return "board/P701/list";
	}
	
	@GetMapping("board/P701/modify")
	public String modify(Model model, int no, int pg,@AuthenticationPrincipal MyUserDetails member) {
		BoardVO vo = service.selectJapanese(no);
		MemberEntity mem = member.getUser();
		model.addAttribute("member", mem);
		model.addAttribute("no", no);
		model.addAttribute("pg", pg);
		model.addAttribute("vo", vo);
		
		return "board/P701/modify";
	}
	
	@PostMapping("board/P701/modify")
	public String modify(BoardVO vo, int no, int pg) {
		
		service.updateJapanese(vo);
		return "redirect:/board/P701/view?no=" + no+"&pg="+pg;
	}
	
	@GetMapping("board/P701/view")
	public String view(Model model, int no, int pg,@AuthenticationPrincipal MyUserDetails member) {
		MemberEntity mem = member.getUser();
		BoardVO vo = service.selectJapanese(no);
		//댓글 보기
		List<BoardVO> comment = service.selectComment(no);
		
		
		//댓글 카운트
		//int countComment = service.countComment(no);
		service.hitJapanese(vo);
		model.addAttribute("countComment", comment.size());
		model.addAttribute("comment", comment);
		model.addAttribute("member", mem);
		model.addAttribute("no", no);
		model.addAttribute("pg", pg);
		model.addAttribute("vo", vo);
		return "board/P701/view";
	}
		

	@ResponseBody
	@PostMapping("board/P701/comment")
	public List<BoardVO> comment(BoardVO vo) {
		service.insertComment(vo);
		List<BoardVO> comments =service.selectComment(vo.getParent());
		
		//Map<String, Integer> map = new HashMap<>();
		//map.put("result", result);
		
		return comments;
	}
	
	@ResponseBody
	@PutMapping("board/P701/updatecomment")
	public Map<String, String> updatecomment(BoardVO vo){
		service.updateComment(vo);
		String content = vo.getContent();
		
		Map<String, String> map = new HashMap<>();
		map.put("content", content);
		return map;
	}
	
	@ResponseBody
	@GetMapping("board/P701/commentDelete")
	public List<BoardVO> commentDelete(Model model, BoardVO vo, int no) {
	    service.deleteJapanese(no);
	    System.out.println("vo" + vo.getParent());
	    List<BoardVO> comments = service.selectComment(vo.getParent());
	    model.addAttribute("no", no);

	    return comments;
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
	
	@GetMapping("board/P701/delete")
	public String delete(Model model, int no) {
		
		service.deleteJapanese(no);
		model.addAttribute("no", no);

		return "redirect:/board/P701/list";
	}

}
