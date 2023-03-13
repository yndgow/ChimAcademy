package kr.co.ChimAcademy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.ChimAcademy.service.Ebook_ArticleService;
import kr.co.ChimAcademy.vo.Ebook_ArticleVO;
import kr.co.ChimAcademy.vo.Ebook_Article_fileVO;

@Controller
public class ELibController {

	@Autowired
	private Ebook_ArticleService aService;
	
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
	/*전자도서 공지사항 게시판*////////////////////
	@GetMapping("elib/info/list")
	public String noticeList(Model model, String pg) {
		int currentPage = aService.getCurrnetPage(pg);
		int start = aService.getLimitStart(currentPage);
		int total = aService.selectCountTotal();
		int pageStartNum = aService.getPageStartNum(total, start);
		int lastPageNum = aService.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = aService.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = aService.getPageGroup(currentPage, lastPageNum)[1];
		
		
		List<Ebook_ArticleVO> articles = aService.selectArticles(start);
		List<Ebook_ArticleVO> notices = aService.selectNotices();
		
		model.addAttribute("pg", pg);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageGroupStart", pageGroupStart);
		model.addAttribute("pageGroupEnd", pageGroupEnd);
		model.addAttribute("articles", articles);
		model.addAttribute("notices", notices);
		return "elib/info/list";
	}
	@GetMapping("elib/info/write")
	public String noticeWrite() {
		return "elib/info/write";
	}
	@PostMapping("elib/info/write")
	public String write(Ebook_ArticleVO vo, HttpServletRequest req) {
		String regip = req.getRemoteAddr();
		vo.setRegip(regip);
		
		aService.insertArticle(vo);
		return "redirect:/elib/info/list";
	}
	@GetMapping("elib/info/modify")
	public String noticeModify(Model model, int no) {
		Ebook_ArticleVO article = aService.selectArticle(no);
		model.addAttribute("article",article);
		return "elib/info/modify";
	}
	@PostMapping("elib/info/modify")
	public String noticeModify(Ebook_ArticleVO vo) {
		aService.updateArticle(vo);
		String no = vo.getNo()+"";
		return "redirect:/elib/info/view?no="+no;
	}
	@GetMapping("elib/info/view")
	public String noticeView(Model model, int no) {
		// 게시물 들고오기
		Ebook_ArticleVO article = aService.selectArticle(no);
		model.addAttribute("article",article);
		// 게시물 조회수 +1
		aService.updateArticleHit(no);
		return "elib/info/view";
	}
	@GetMapping("elib/info/download")
	public ResponseEntity<Resource> download(int fno) throws IOException {
		Ebook_Article_fileVO vo =aService.selectFile(fno);
		aService.updateFileDownload(fno);
		ResponseEntity<Resource> respEntity = aService.fileDownload(vo);
		return respEntity;
	}
	@GetMapping("elib/info/how")
	public String how() {
		return "elib/info/how";
	}
	@GetMapping("elib/info/install")
	public String install() {
		return "elib/info/install";
	}
	
	/*내서재*/////////////////////////
	@GetMapping("elib/mylibrary/mylib")
	public String mylib() {
		return "elib/mylibrary/mylib";
	}
}
