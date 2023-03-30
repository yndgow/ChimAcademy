package kr.co.ChimAcademy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.dto.EvalBoardDTO;
import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.dto.SyllabusDto;
import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.LectureEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.entity.ScoreEntity;
import kr.co.ChimAcademy.service.ProfessorService;
import kr.co.ChimAcademy.vo.MemberVO;
import kr.co.ChimAcademy.vo.ScoreVO;

@Controller
public class ProfessorController {
	@Autowired
	private ProfessorService service;

	@GetMapping("professor/credit")
	public String credit(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		// 교수 - 강의 내역
		List<LecListEntity> list = service.selectClasss(userDetails.getUser().getUid());
		List<LecSugangDto> list2 = new ArrayList<>();
		for(LecListEntity ele : list) {

			// 구분 변환
			LecSugangDto dto = new LecSugangDto();
			LectureEntity entity = ele.getLectureEntity();
			dto.setLecName(entity.getLecName());
			dto.setLecClass(entity.getLecClass());
			dto.setLecGubun(entity.getLecGubun());
			dto.setCredit(entity.getCredit());
			dto.setBeginTime(entity.getBeginTime());
			dto.setEndTime(entity.getEndTime());
			dto.setLecRequest(entity.getLecRequest());
			dto.setLecDay(entity.getLecDay());
			dto.setLecLoc(entity.getLecLoc());
			dto.setLecCode(entity.getLecCode());
			list2.add(dto);
		}
		model.addAttribute("classs", list2);
		
		return "professor/credit";
	}
	
	@ResponseBody
	@GetMapping("professor/credit/{lecCode}")
	public List<ScoreEntity> selectScoresBylecCode(@PathVariable int lecCode){
		return service.selectScoresBylecCode(lecCode);
	}

	@GetMapping("professor/eval")
	public String eval(@RequestParam(defaultValue = "0") int lecCode, @AuthenticationPrincipal MyUserDetails details,
			Model model) {
		String uid = details.getUsername();

		List<EvalBoardDTO> boards = service.selectEvals(uid, lecCode);

		model.addAttribute("boards", boards);

		return "professor/eval";
	}

	@GetMapping("professor/manage")
	public String manage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		// 교수 - 강의 내역
		List<LecListEntity> list = service.selectClasss(userDetails.getUser().getUid());
		List<LecSugangDto> list2 = new ArrayList<>();
		for(LecListEntity ele : list) {

			// 구분 변환
			LecSugangDto dto = new LecSugangDto();
			LectureEntity entity = ele.getLectureEntity();
			dto.setLecName(entity.getLecName());
			dto.setLecClass(entity.getLecClass());
			dto.setLecGubun(entity.getLecGubun());
			dto.setCredit(entity.getCredit());
			dto.setBeginTime(entity.getBeginTime());
			dto.setEndTime(entity.getEndTime());
			dto.setLecRequest(entity.getLecRequest());
			dto.setLecDay(entity.getLecDay());
			dto.setLecLoc(entity.getLecLoc());
			dto.setLecCode(entity.getLecCode());
			list2.add(dto);
		}
		model.addAttribute("classs", list2);
		return "professor/manage";
	}

	@GetMapping("professor/my")
	public String mypage(Model model, @AuthenticationPrincipal MyUserDetails member) {
		String uid = member.getUser().getUid();

		MemberVO vo = service.selectProMy(uid);
		List<LecSugangDto> lecture = service.selectProlecture(uid);

		model.addAttribute("uid", uid);
		model.addAttribute("professor", vo);
		model.addAttribute("professorlec", lecture);

		return "mypage/professor/my";
	}

	@GetMapping("professor/my/modify")
	public String mypagemodify(@AuthenticationPrincipal MyUserDetails member, Model model) {
		MemberEntity mem = member.getUser();
		MemberVO vo = service.selectProMy(mem.getUid());

		model.addAttribute("professor", vo);
		model.addAttribute("member", mem);

		return "mypage/professor/modify";
	}

	// 상세정보 업데이트
	@PostMapping("professor/my/modify")
	public String mypagemodify(MemberVO vo, @AuthenticationPrincipal MyUserDetails member) {
		//MemberEntity mem = member.getUser();
		//String uid = mem.getUid();

		service.updateProMy(vo);

		//String career = vo.getCareer();
		service.updateProMyinfo(vo);

		return "redirect:/professor/my/modify";
	}

	// 이미지 업데이트
	@PostMapping("professor/my/modifyProfile")
	public String insertProfile(@AuthenticationPrincipal MyUserDetails member, MemberVO vo) {
		
		String nName = service.updateProfile(vo);
		member.getUser().setProfile(nName);
		return "redirect:/professor/my/modify";
	}

	// 강의 계획서 작성 팝업 띄우기
	@GetMapping("professor/class/write")
	public String writeClassSc() {
		return "syllabus/write";
	}
	
	// 강의 계획서 입력
	@PostMapping("professor/class/write")
	public String writeSyllabus(SyllabusDto dto) {

		// 파일 && 계획서 업로드
		service.insertBoardSyllabus(dto);
		
		return "redirect:/professor/manage";
	}

	// 강의 평가 출력
	@GetMapping("professor/eval/view")
	public String evalView(int no, Model model) {
		EvalBoardDTO vo  = service.selectEvalView(no);
		model.addAttribute("vo", vo);
		return "professor/evalView";
	}
	
	// 성적 입력 팝업
	@GetMapping("professor/score/{no}")
	public String scoreView(@PathVariable int no, Model model) {
		model.addAttribute("score", service.selectScore(no));
		return "professor/score";
	}
	
	// 성적 입력하기
	@PostMapping("professor/score")
	public String insertScore(ScoreVO vo) {
		service.updateScore(vo);
		return "redirect:/professor/credit";
	}
	
}
