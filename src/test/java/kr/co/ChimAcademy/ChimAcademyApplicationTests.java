package kr.co.ChimAcademy;


import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ChimAcademy.dto.LecSugangDto;
import kr.co.ChimAcademy.entity.BoardEntity;
import kr.co.ChimAcademy.entity.DepartmentEntity;
import kr.co.ChimAcademy.entity.LecFileEntity;
import kr.co.ChimAcademy.entity.LecListEntity;
import kr.co.ChimAcademy.entity.Lec_SugangEntity;
import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.repository.BoardRepo;
import kr.co.ChimAcademy.repository.LecFileRepo;
import kr.co.ChimAcademy.repository.LecListRepo;
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
	@Autowired
	private LecListRepo lecListRepo;
	@Autowired
	private LecFileRepo fileRepo;
	@Autowired
	private BoardRepo boardRepo;


	@Test
	public void test() {
		 
		
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setContent("Test22");
		boardEntity.setMemberEntity(memberRepo.findById("qwer").get());
		boardEntity.setFileEntity(fileRepo.findById(2).get());
				
		boardRepo.save(boardEntity);		//		log.info("newName :" + board.getFileEntity().getNewName());
		
	}
	
	
	
	
	
	

}
