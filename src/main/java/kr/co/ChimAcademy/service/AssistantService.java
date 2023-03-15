package kr.co.ChimAcademy.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.AssistantDAO;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.repository.MemberRepo;
import kr.co.ChimAcademy.vo.DepartmentVO;
import kr.co.ChimAcademy.vo.MemberVO;

@Service
public class AssistantService {

	private final AssistantDAO dao;
	private final MemberRepo memberRepo;
	private final PasswordEncoder encoder;

	public AssistantService(AssistantDAO dao, MemberRepo memberRepo, PasswordEncoder encoder) {
		this.dao = dao;
		this.memberRepo = memberRepo;
		this.encoder = encoder;
	}

	// 학과 이름 가져오기
	public DepartmentVO selectDep(String uid) {
		return dao.selectDep(uid);
	}

	// 멤버 여러건 추가하기(JPA, 비밀번호 자동 넣기 불편)
	public List<MemberEntity> insertMembers(List<MemberEntity> members) {

		for (MemberEntity member : members) {
			SecureRandom random = new SecureRandom();
			byte[] bytes = new byte[12];
			random.nextBytes(bytes);
			String pass = Base64.getEncoder().encodeToString(bytes);
			pass = encoder.encode(pass);
			member.setPass(pass);
			member.setStatus(1);
		}
		return memberRepo.saveAll(members);
	}

	// 멤버 여러건 추가하기(Mybatis)
	public int insertMems(List<MemberVO> members) {
		return 0;
	}
}
