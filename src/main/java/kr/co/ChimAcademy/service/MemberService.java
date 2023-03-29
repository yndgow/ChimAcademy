package kr.co.ChimAcademy.service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.mapper.MemberUidMapping;
import kr.co.ChimAcademy.repository.MemberRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	private final MemberRepo memberRepo;
	private final PasswordEncoder encoder;

	public MemberService(MemberRepo memberRepo, PasswordEncoder encoder) {
		this.memberRepo = memberRepo;
		this.encoder = encoder;
	}
	
	
	// 학번, 아이디 찾기
	public MemberUidMapping searchUid(String name, String birth, String hp) {
		return memberRepo.findByNameAndBirthAndHp(name, birth, hp);
	}
	
	// 이메일 중복여부 체크
	public int countEmail(String email) {
		return memberRepo.countByEmail(email);
	}
	
	
	// 가입하기
	@Transactional
	public void updateMember(MemberEntity entity, HttpServletRequest req) {
		MemberEntity member = memberRepo.findById(entity.getUid()).get();
		log.info("entity:" + entity);
		log.info("member:" + member);
		
		// 실제 아이피 주소 가져오기
		String ipAddress = req.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = req.getRemoteAddr();
        } else {
            ipAddress = ipAddress.split(",")[0].trim();
        }
		
		member.setGender(entity.getGender());
		member.setPass(encoder.encode(entity.getPass()));
		member.setEmail(entity.getEmail());
		member.setZip(entity.getAddr1());
		member.setAddr1(entity.getAddr1());
		member.setAddr2(entity.getAddr2());
		member.setRegip(ipAddress);
		member.setProfile("myinfo.png");
		log.info("member:" + member);
	}
	
	
	@Autowired
	private JavaMailSender javaMailSender;

	public int sendEmail(String to) throws MessagingException {
		
		
		// 인증번호 생성
		int code = ThreadLocalRandom.current().nextInt(100000,1000000);
		
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
	    helper.setTo(to);
	    helper.setSubject("침대학교 인증메일입니다.");
	    helper.setText("침대학교 인증번호는 " + code + " 입니다.");
	    try {
			helper.setFrom("yndgow@gmail.com", "관리자");
		} catch (UnsupportedEncodingException | MessagingException e) {
			log.error(e.getMessage());
		}
	    javaMailSender.send(message);
	    
	    return code;
	}
}
