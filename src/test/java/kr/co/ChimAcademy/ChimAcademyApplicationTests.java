package kr.co.ChimAcademy;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ChimAcademy.entity.ProfessorEntity;
import kr.co.ChimAcademy.repository.MemberRepo;
import kr.co.ChimAcademy.repository.ProfessorRepo;
import kr.co.ChimAcademy.service.MemberService;

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
		ProfessorEntity entity = professorRepo.findById(1).orElse(null);
		System.out.println(entity.getMemberEntity().getName());
	}
	
	
	
	
	
	

}
