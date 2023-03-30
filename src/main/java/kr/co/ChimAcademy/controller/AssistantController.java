package kr.co.ChimAcademy.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ChimAcademy.config.MyUserDetails;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.LectureEntity;
import kr.co.ChimAcademy.entity.MajorEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.service.AssistantService;
import kr.co.ChimAcademy.vo.DepartmentVO;
import kr.co.ChimAcademy.vo.LectureVO;
import kr.co.ChimAcademy.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AssistantController {

	private final AssistantService assistantService;
	
	public AssistantController(AssistantService assistantService) {
		this.assistantService = assistantService;
	}
	

	// 인원 추가페이지 이동
	@GetMapping("assistant/insert")
	public String insert(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		
		MemberEntity member = userDetails.getUser();
		DepartmentVO vo = assistantService.selectDep(member.getUid());
		
		model.addAttribute("department", vo);
		model.addAttribute("member", member);
		
		return "assistant/insert";
	}
	
	// 인원 추가 여러건
	@ResponseBody
	@PostMapping("assistant/insert")
	public List<MemberEntity> insertMembers(@RequestBody List<MemberEntity> members) {
		log.info("entity : " + members);
		return assistantService.insertMembers(members);
	}
	
	
	// 학생 목록 출력
	@GetMapping("assistant/manage")
	public String manage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		String depCode = userDetails.getUser().getDepartmentEntity().getDepCode();
		List<MemberVO> members = assistantService.selectMembers(depCode);
		MemberEntity member = userDetails.getUser();
		DepartmentVO vo = assistantService.selectDep(member.getUid());
		model.addAttribute("members", members);
		model.addAttribute("department", vo);
		return "assistant/manage";
	}
	
	// 학생 정보 수정
	@GetMapping("assistant/modify")
	public String modifyGet(String uid, Model model) {
		MemberVO member = assistantService.selectMember(uid);
		try {
			member.setRdate(member.getRdate().substring(0,10));
			member.setWdate(member.getWdate().substring(0,10));
		} catch (Exception e) {
			log.info("error : " + e.getMessage());
		}
		model.addAttribute("member", member);
		return "assistant/modify";
	}
	@PostMapping("assistant/modify")
	public String modifyPost(MemberVO vo) {
		log.info("wdate : "+vo.getWdate()+"////////////////////////////////////");
		assistantService.updateMember(vo);
		
		return "redirect:/assistant/manage";
	}
	
	// 강의 관리 
	@GetMapping("assistant/lecuture")
	public String lecuture(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
		// 로그인한 조교의 학과
		DepartmentVO vo = assistantService.selectDep(userDetails.getUser().getUid());
		// 학과의 전공
		List<MajorEntity> list = assistantService.selectMajors(vo.getDepCode());
		// 학과의 교수
		DepartmentEntity departmentEntity = DepartmentEntity.builder().depCode(vo.getDepCode()).build();
		List<MemberEntity> professors = assistantService.selectProfessors(departmentEntity);
		
		model.addAttribute("department", vo);
		model.addAttribute("majors", list);
		model.addAttribute("professors", professors);
		return "assistant/lecuture";
	}

	// 과목 리스트
	@ResponseBody
	@GetMapping("assistant/lecture/search")
	public List<LectureVO> selectLectures(LectureVO vo){
		
		List<LectureVO> lectures = assistantService.selectLectures(vo);
		
		return lectures;
	}
	
	@PostMapping("assistant/lecture/register")
	public String lecRegister(LectureEntity entity, MemberEntity memberEntity) {
		
		log.info("entity : " + entity);
		log.info("uid : " + memberEntity.getUid());
		
		if(memberEntity.getUid() != null) {
			assistantService.insertLectureList(entity, memberEntity);
		}else {
			// 강의 등록
			assistantService.insertLecture(entity);
		}
		
		return "redirect:/assistant/lecuture";
	}
	
	// 과목 1개 가져오기
	@ResponseBody
	@GetMapping("assistant/lecture/{lecCode}")
	public ResponseEntity<?> getLecture(@PathVariable int lecCode) {
		LectureEntity lectureEntity = assistantService.getLectureEn(lecCode);
		LecListEntity lecListEntity = assistantService.getLecture(lectureEntity);
		
		log.info("lecCode : " + lectureEntity);
		log.info("entity : " + lectureEntity);
		if(lecListEntity == null) {
			return new ResponseEntity<>(lectureEntity, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(lecListEntity, HttpStatus.OK);
		}
		 
	}
	
	
	// 과목 수정하기
	@PostMapping("assistant/lecture/modify")
	public String updateLecture(MemberEntity memberEntity, LectureEntity lectureEntity, @RequestParam(defaultValue = "0") int no) {
		if(memberEntity.getUid() == null) {
			assistantService.insertLecture(lectureEntity);
		}else {
			assistantService.updateLecture(lectureEntity, memberEntity, no);
		}
		return "redirect:/assistant/lecuture";
	}
	
	@ResponseBody
	@DeleteMapping("assistant/lecture/{lecCode}")
	public ResponseEntity<Integer> delLecture(@PathVariable int lecCode) {
		assistantService.deleteLecture(lecCode);
		return new ResponseEntity<>(1, HttpStatus.OK);
	}
}
