package kr.co.ChimAcademy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.service.EbookService;
import kr.co.ChimAcademy.service.Ebook_ArticleService;
import kr.co.ChimAcademy.vo.CountVO;
import kr.co.ChimAcademy.vo.EbookCate1VO;
import kr.co.ChimAcademy.vo.EbookCate2VO;
import kr.co.ChimAcademy.vo.EbookFileVO;
import kr.co.ChimAcademy.vo.EbookVO;
import kr.co.ChimAcademy.vo.Ebook_ArticleVO;
import kr.co.ChimAcademy.vo.Ebook_Article_fileVO;
import kr.co.ChimAcademy.vo.MylibVO;

@Controller
public class ELibController {

	@Autowired
	private EbookService eService;
	
	@Autowired
	private Ebook_ArticleService aService;
	
	@GetMapping(value = {"elib/","elib/index"})
	public String index() {
		return "elib/index";
	}
	
	@GetMapping("elib/ebook/list")
	public String list(Model model,String pg,String sort,String type,EbookVO vo) {
		if(sort == null) {
			sort = "4";
		}
		if(type == null) {
			type = "1";
		}
		if(vo.getGROUP() == null) {
			vo.setGROUP("ebook");
			vo.setCate1(10);
			vo.setCate2(10);
			vo.setApplier("교보문고");
		}
		int currentPage = eService.getCurrnetPage(pg);
		int start = eService.getLimitStart(currentPage);
		int total = eService.selectCountTotal(sort,vo);
		int pageStartNum = eService.getPageStartNum(total, start);
		int lastPageNum = eService.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = eService.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = eService.getPageGroup(currentPage, lastPageNum)[1];
		
		model.addAttribute("pg", pg);
		model.addAttribute("total", total);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageGroupStart", pageGroupStart);
		model.addAttribute("pageGroupEnd", pageGroupEnd);
		model.addAttribute("sort", sort);
		model.addAttribute("type", type);
		model.addAttribute("vo", vo);
		
		List<EbookVO> ebooks =eService.selectEbooks(sort,type,vo,start);
		List<EbookCate1VO> cate1s = eService.selectCate1s();
		List<EbookCate2VO> cate2s = eService.selectCate2s(vo.getCate1());
		model.addAttribute("ebooks",ebooks);
		model.addAttribute("cate1s",cate1s);
		model.addAttribute("cate2s",cate2s);
		
		CountVO counts = eService.selectCountEbooks();
		model.addAttribute("counts",counts);
		return "elib/ebook/list";
	}
	@GetMapping("elib/ebook/view")
	public String view(@AuthenticationPrincipal MyUserDetails member,Model model, String pg, String sort, String type, EbookVO vo) {
		model.addAttribute("member", member.getUser());
		EbookVO ebook = eService.selectEbook(vo.getBookId());
		model.addAttribute("ebook",ebook);
		model.addAttribute("bookId",vo.getBookId());
		CountVO counts = eService.selectCountEbooks();
		model.addAttribute("counts",counts);
		model.addAttribute("sort",sort);
		model.addAttribute("type",type);
		model.addAttribute("pg",pg);
		model.addAttribute("vo",vo);
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
	@GetMapping("elib/info/register")
	public String register(Model model) {
		List<EbookCate1VO> cate1s = eService.selectCate1s();
		model.addAttribute("cate1s",cate1s);
		return "elib/info/register";
	}
	@PostMapping("elib/info/register")
	public String register(EbookVO vo) {
		eService.insertEbook(vo);
		return "redirect:/elib/ebook/list";
	}
	@ResponseBody
	@RequestMapping(value="elib/info/cate2s", method = {RequestMethod.POST})
	public Map<String, List<EbookCate2VO>> cate2s(int c1) {
		List<EbookCate2VO> cate2s = eService.selectCate2s(c1);
		Map<String, List<EbookCate2VO>> map = new HashMap<>();
		map.put("cate2s", cate2s);
		return map;
	}
	@GetMapping("elib/info/delete")
	public String delete(int no) {
		// 게시물 삭제
		aService.deleteArticle(no);
		// 게시물 파일 DB 삭제
		Ebook_Article_fileVO vo = aService.deleteFile(no);
		if(vo != null) {
		// 게시물 실제 파일 삭제
		aService.deleteRealFile(vo);
		}
		return "redirect:/elib/info/list";
	}
	
	/*내서재*/////////////////////////
	@GetMapping("elib/mylibrary/mylib")
	public String mylib(@AuthenticationPrincipal MyUserDetails member, Model model,String state,String pg) {
		if(state ==null) {
			state = "1";
		}
		String uid = member.getUser().getUid();
		int currentPage = eService.getCurrnetPage(pg);
		int start = eService.getLimitStart(currentPage);
		int total = eService.selectCountTotalMylibs(uid,state);
		int pageStartNum = eService.getPageStartNum(total, start);
		int lastPageNum = eService.getLastPageNum(total);
		
		// 페이지 그룹 start, end 번호
		int pageGroupStart = eService.getPageGroup(currentPage, lastPageNum)[0];
		int pageGroupEnd = eService.getPageGroup(currentPage, lastPageNum)[1];
		
		model.addAttribute("pg", currentPage);
		model.addAttribute("total", total);
		model.addAttribute("state", state);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageGroupStart", pageGroupStart);
		model.addAttribute("pageGroupEnd", pageGroupEnd);
		
		List<MylibVO> mylibs = eService.selectMylibs(uid,state,start);
		model.addAttribute("mylibs",mylibs);
		return "elib/mylibrary/mylib";
	}
	@ResponseBody
	@PostMapping("elib/mylibrary/register")
	public Map<String, Integer> registerMylib(MylibVO vo) {
		int result = eService.insertMylib(vo);
		if(vo.getState() == 1) {
			eService.updateEbookLoan(2, vo.getBookId());
		}else if(vo.getState() == 2){
			eService.updateEbookReserv(2, vo.getBookId());
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	@ResponseBody
	@PostMapping("elib/ebook/like")
	public Map<String, Integer> like(String bookId) {
		int result = eService.updateEbookLike(bookId);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	@GetMapping("elib/mylibrary/open")
	public ResponseEntity<Resource> Open(String bookId) throws IOException {
		EbookFileVO vo =eService.selectEbookFile(bookId);
		eService.updateEbookDown(bookId);
		ResponseEntity<Resource> respEntity = eService.fileOpen(vo);
		return respEntity;
	}
}
