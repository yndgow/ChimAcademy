package kr.co.ChimAcademy.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriUtils;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.entity.LecFileEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.StudentService;
import kr.co.ChimAcademy.vo.EvalBoardVO;
import kr.co.ChimAcademy.vo.MemberVO;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	@GetMapping("student/class/signup2")
	public String signUp() {
		return "student/signUp";
	}
	
	@GetMapping("student/class/timeTable")
	public String timeTable(@AuthenticationPrincipal MyUserDetails member,Model model){
		List<LecSugangDto> table = service.selectSugangs(member.getUser().getUid());
		model.addAttribute("table",table);
		return "student/timeTable";
	}
	
	@GetMapping("student/class/eval")
	public String eval() {
		return "student/eval";
	}
	
	@GetMapping("student/my")
	public String mypage(Principal pricipal, Model model) {
		
		String uid = pricipal.getName();
		MemberVO vo = service.selectStudent(uid);
		List<LecSugangDto> lecture = service.selectLectures(uid);
		
		model.addAttribute("vo", vo);
		model.addAttribute("lecture", lecture);
		return "mypage/student/my";
	}
	
	@PostMapping("student/my")
	public String mypage(EvalBoardVO vo, String pid) {
		vo.setUid(pid);
		
		service.insertLecEval(vo);
		
		return "redirect:/student/my";
	}
	
	@GetMapping("student/my/modify")
	public String mypagemodify(@AuthenticationPrincipal MyUserDetails member, Model model) {
		MemberEntity mem = member.getUser();
		MemberVO vo = service.selectStudent(mem.getUid());
		
		model.addAttribute("vo", vo);
		model.addAttribute("member", mem);

		return "mypage/student/modify";
	}
	// 이미지 업데이트
	@PostMapping("student/my/modifyProfile")
	public String insertProfile(@AuthenticationPrincipal MyUserDetails member, MemberVO vo) {
		
		String nName = service.updateProfile(vo);
		member.getUser().setProfile(nName);
		return "redirect:/student/my/modify";
	}
	
	// 상세정보 업데이트
	@PostMapping("student/my/modify")
	public String mypagemodify(MemberVO vo, @AuthenticationPrincipal MyUserDetails member) {
		//MemberEntity mem = member.getUser();
		//String uid = mem.getUid();
		
		service.updateStudent(vo);
		
		return "redirect:/student/my/modify";
	}

	// 강의 계획서 보기
	@GetMapping("student/class/view")
	public String viewSyllabus(int lecCode, Model model) {
		BoardEntity boardEntity = service.selectSyllabus(lecCode);
		model.addAttribute("board", boardEntity);
		return "syllabus/view";
	}
	
	
	@GetMapping("syllabus/download")
	public ResponseEntity<Resource> downloadFile(int no) throws IOException {
	    // Get the file as a resource from the classpath or filesystem
		
		// 파일정보 찾아오기
		LecFileEntity file= service.selectFile(no);
		
		// 경로
		String path = "file/2023/";
		// 현재 파일이름
		String nName = file.getNewName();
		// 원래 파일이름
		String oName = file.getOriName();
		
	    Resource resource = new FileSystemResource(path + nName);

	    // Set the response headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + UriUtils.encode(oName, StandardCharsets.UTF_8));

	    // Return the file as a ResponseEntity
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(resource.contentLength())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);
	}

}
