package kr.co.ChimAcademy;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.Lec_SugangEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.repository.Lec_SugangRepo;
import kr.co.ChimAcademy.repository.MemberRepo;
import kr.co.ChimAcademy.repository.ProfessorRepo;
import kr.co.ChimAcademy.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ChimAcademyApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private ProfessorRepo professorRepo;
	@Autowired
	private Lec_SugangRepo repo;
	
	@Test
	public void test() {
		LecSugangDto dto = new LecSugangDto();
		dto.setBeginTime(1);
		dto.setEndTime(3);
		
		log.info("ctime : " + dto.getCTime());
		
		
		
	}
	
	
	
	
	
	

}
