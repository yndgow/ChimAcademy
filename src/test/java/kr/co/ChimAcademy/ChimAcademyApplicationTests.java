package kr.co.ChimAcademy;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ChimAcademy.repository.MemberRepo;
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
	
	@Test
	public void test() {
		int result = memberRepo.countByEmail("test2@gmail.com");
		System.out.println(result);
	}
	
	
	
	
	
	

}
