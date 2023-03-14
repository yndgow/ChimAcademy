package kr.co.ChimAcademy;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ChimAcademy.service.MemberService;

@SpringBootTest
class ChimAcademyApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
	@Autowired
	private MemberService memberService;
	
	@Test
	public void sendEmail() throws MessagingException {
		String to = "tagetwin@naver.com";
		String subject = "test메일입니다.";
		String text = "test";
		memberService.sendEmail(to);
		
	}
	
	
	
	

}
