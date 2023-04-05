package kr.co.ChimAcademy.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import kr.co.ChimAcademy.vo.ItemVO;
import kr.co.ChimAcademy.vo.MylibVO;
import kr.co.ChimAcademy.vo.ResultVO;

@Controller
public class ELibController {

	@Autowired
	private EbookService eService;
	
	@Autowired
	private Ebook_ArticleService aService;
	
	@GetMapping(value = {"elib/","elib/index"})
	public String index(Model model) {
		// 도서관 소식 불러오기
		List<Ebook_ArticleVO> articles = aService.selectArticles(0);
		model.addAttribute("articles",articles);
		// 인덱스 도서 목록 불러오기
		EbookVO vo = new EbookVO();
		vo.setGROUP("ebook");
		List<EbookVO> ebooks = eService.selectEbooks("4", "1", vo, 0);
		vo.setGROUP("audio");
		List<EbookVO> audios = eService.selectEbooks("4", "1", vo, 0);
		model.addAttribute("ebooks",ebooks);
		model.addAttribute("audios",audios);
		//부산 도서관 정보 공공API///////////////
		ItemVO[] items = eService.LibAPI();
		model.addAttribute("items",items);
		return "elib/index";
	}
	@ResponseBody
	@PostMapping("elib/selectIdxs")
	public Map<String, List<EbookVO>> selectIdxs(String type) {
		EbookVO vo = new EbookVO();
		vo.setGROUP("ebook");
		List<EbookVO> idx1 = eService.selectEbooks("4", type, vo, 0);
		vo.setGROUP("audio");
		List<EbookVO> idx2 = eService.selectEbooks("4", type, vo, 0);
		List<EbookVO> list = new ArrayList<>();
		for(int i=0;i<3;i++) {
			list.add(idx1.get(i));
		}
		for(int i=0;i<3;i++) {
			list.add(idx2.get(i));
		}
		Map<String, List<EbookVO>> map = new HashMap<>();
		map.put("list", list);
		return map;
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
		
		CountVO counts = eService.selectCountEbooks(vo.getGROUP());
		model.addAttribute("counts",counts);
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
		return "elib/ebook/list";
	}
	@GetMapping("elib/ebook/search")
	public String search(Model model,String pg,String keyword) {
		int currentPage = eService.getCurrnetPage(pg);
		int start = eService.getLimitStart(currentPage);
		int total = eService.selectCountTotalSearch(keyword);
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
		
		List<EbookVO> ebooks = eService.selectEbooksSearch(keyword, start);
		model.addAttribute("ebooks",ebooks);
		model.addAttribute("keyword",keyword);
		
		//부산 도서관 정보 공공API///////////////
		ItemVO[] items = eService.LibAPI();
		model.addAttribute("items",items);
		return "elib/ebook/search";
	}
	@GetMapping("elib/ebook/view")
	public String view(@AuthenticationPrincipal MyUserDetails member,Model model, String pg, String sort, String type, EbookVO vo) {
		model.addAttribute("member", member.getUser());
		String uid = member.getUser().getUid();
		// 학생이 해당 책을 이미 대출했는지 확인 ( result1 > 0 대출 중이다.)
		int result1 = eService.selectCountForCheckMylib(uid, vo.getBookId(), "1");
		// 학생이 해당 책을 이미 예약했는지 확인 ( result2 > 0 예약 중이다.)
		int result2 = eService.selectCountForCheckMylib(uid, vo.getBookId(), "2");
		// 학생이 해당 책을 이미 관심도서에 추가했는지 확인 ( result3 > 0 관심도서 목록에 이미 있다.)
		int result3 = eService.selectCountForCheckMylib(uid, vo.getBookId(), "0");
		// 학생이 해당 책을 이미 좋아요했는지 확인 ( result3 > 0 관심도서 목록에 이미 있다.)
		int result4 = eService.selectCountForCheckMylib(uid, vo.getBookId(), "4");
		// 도서 정보 불러오기
		EbookVO ebook = eService.selectEbook(vo.getBookId());
		model.addAttribute("ebook",ebook);
		model.addAttribute("bookId",vo.getBookId());
		// lnb 등록된 책 권수 불러오기
		CountVO counts = eService.selectCountEbooks(vo.getGROUP());
		model.addAttribute("counts",counts);
		model.addAttribute("sort",sort);
		model.addAttribute("type",type);
		model.addAttribute("pg",pg);
		model.addAttribute("vo",vo);
		model.addAttribute("result1",result1);
		model.addAttribute("result2",result2);
		model.addAttribute("result3",result3);
		model.addAttribute("result4",result4);
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
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
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
		return "elib/info/list";
	}
	@GetMapping("elib/info/write")
	public String noticeWrite(Model model) {
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
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
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
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
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
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
	public String how(Model model) {
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
		return "elib/info/how";
	}
	@GetMapping("elib/info/install")
	public String install(Model model) {
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
		return "elib/info/install";
	}
	@GetMapping("elib/info/register")
	public String register(Model model) {
		List<EbookCate1VO> cate1s = eService.selectCate1s();
		model.addAttribute("cate1s",cate1s);
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
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
		//부산 도서관 정보 공공API///////////////
				ItemVO[] items = eService.LibAPI();
				model.addAttribute("items",items);
		return "elib/mylibrary/mylib";
	}
	// 책 등록하기(대출,연장)
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
	// 좋아요버튼 누를 시 +1 like
	@ResponseBody
	@PostMapping("elib/ebook/like")
	public Map<String, Integer> like(String uid, String bookId) {
		int result = eService.updateEbookLike(uid,bookId);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	// 책 반납하기
	@ResponseBody
	@PostMapping("elib/mylibrary/return")
	public Map<String, Integer> returnMylib(int no) {
		int result = eService.updateMylibReturn(no);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	// 전자책 읽기
	@GetMapping("elib/mylibrary/openEbook")
	public ResponseEntity<Resource> OpenEbook(String bookId,String group) throws IOException {
		EbookFileVO vo =eService.selectEbookFile(bookId);
		eService.updateEbookDown(bookId);
		ResponseEntity<Resource> respEntity = eService.fileOpen(vo,group);
		return respEntity;
	}
	// 책 연장하기
	@ResponseBody
	@PostMapping("elib/mylibrary/extension")
	public Map<String, Integer> extension(int no) {
		int result = eService.updateMylibReturnDate(no);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
}
