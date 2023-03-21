package kr.co.ChimAcademy;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ChimAcademy.entity.MemberEntity;
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
	
	@Test
	public void test() {
		String id = "qwer";
		MemberEntity entity= memberRepo.findById(id).get();
		log.info("entity : " + entity);
		log.info("dep : " + entity.getDepartmentEntity().getDepName());
	}
	
	
	
	
	
	

}
