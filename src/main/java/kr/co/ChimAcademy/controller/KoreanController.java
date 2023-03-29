package kr.co.ChimAcademy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.KoreanService;
import kr.co.ChimAcademy.vo.BoardVO;

@Controller
public class KoreanController {
	/* A101 국어국문학과 작업용 컨트롤러 - 김훈 20230313 */
	@Autowired
	private KoreanService service;

	/* 국문학과 게시판 목록 출력(List) + 페이징 */
	@GetMapping("board/A101/list")
	public String list(Model model, @RequestParam(defaultValue = "1") String pg) {
		int currentPage = service.getCurrnetPage(pg);
		int start = service.getLimitStrat(currentPage);
		int total = service.selectCount();
		int lastPageNum = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPageNum);
		
		List<BoardVO> vo = service.selectBoards(start);
		model.addAttribute("korean", vo);
		model.addAttribute("pg", pg);
		model.addAttribute("start", start);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPageNum", lastPageNum);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		return "board/A101/list";
	}

	/* 국문학과 게시판 보기(view) */
	@GetMapping("board/A101/view")
	public String A101_view(Model model, int no, int pg, @AuthenticationPrincipal MyUserDetails member) {
		MemberEntity mem = member.getUser();
		// 글
		BoardVO vo = service.selectBoard(no);
		// 조회수 증가
		service.updateBoardHit(vo);
		// 댓글
		List<BoardVO> comment = service.selectBoardComment(no);
		
		model.addAttribute("member", mem);
		model.addAttribute("no", no);
		model.addAttribute("pg", pg);
		model.addAttribute("korean", vo);
		model.addAttribute("comments", comment);
		model.addAttribute("commentCount", comment.size());
		
		return "board/A101/view";
	}

	/* 국문학과 게시판 작성(Write) */
	// 글 작성
	@GetMapping("board/A101/write")
	public String A101_write(@AuthenticationPrincipal MyUserDetails member, Model model) {
		MemberEntity mem = member.getUser();
		model.addAttribute("member", mem);
		return "board/A101/write";
	}

	@PostMapping("board/A101/write")
	public String A101_write(BoardVO vo, HttpServletRequest req) {
		String ip = req.getRemoteAddr();
		vo.setRegip(ip);
		int result = service.insertBoard(vo);
		return "redirect:/board/A101/list";
	}
	
	/* 국문학과 게시판글 삭제(Delete) */
	@ResponseBody
	@PostMapping("board/A101/delete")
	public Map<String, Integer> delete(int no) {
		int result = service.deleteBoard(no);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	/* 국문학과 게시판글 수정(Update) */
	@GetMapping("board/A101/modify")
	public String A101_modify_get(Model model, int no, int pg) {
		BoardVO board = service.selectBoard(no);
		model.addAttribute("korean", board);
		model.addAttribute("pg", pg);
		return "board/A101/modify";
	}
	
	@PostMapping("board/A101/modify")
	public String A101_modify_post(BoardVO vo, int pg) {
		service.updateBoard(vo);
		return "redirect:/board/A101/view?no=" + vo.getNo() + "&pg=" + pg;
	}
	
	/* 국문학과 게시판 댓글 작성 */
	@ResponseBody
	@PostMapping("board/A101/comment")
	public BoardVO board_A101_comment(@AuthenticationPrincipal MyUserDetails member, Model model, BoardVO vo, HttpServletRequest req) {
		String ip = req.getRemoteAddr();
		vo.setRegip(ip);
		
		MemberEntity mem = member.getUser();
		model.addAttribute("member", mem);
		// 작성
		int result = service.insertBoardComment(vo);
		
		// 댓글가져오기
		BoardVO board = service.selectBoard(vo.getNo());
		
		// return 댓글;
		return board;
	}

	/* 국문학과 댓글 삭제 */
	@ResponseBody
	@DeleteMapping("board/A101/comment/delete")
	public Map<String, Integer> deleteComment(int no) {
		int result = service.deleteBoard(no);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map;
	}
	
	/* 국문학과 댓글 수정 */
	@ResponseBody
	@PostMapping("board/A101/comment/modify")
	public Map<String, Integer> modifyBoardComment(BoardVO vo) {
		int result = service.modifyBoardComment(vo);
		Map<String, Integer> json = new HashMap<>();
		json.put("result", result);
		return json;
	}

}
