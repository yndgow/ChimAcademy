package kr.co.ChimAcademy.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ChimAcademy.entity.BoardEntity;

public interface BoardRepo extends JpaRepository<BoardEntity, Integer>{
	Page<BoardEntity> findByTypeAndParent(int type, int parent, Pageable pageable);
	
	// 강의계획서 보기
	BoardEntity findByLecCode(int lecCode);
//	
//	
//	List<BoardEntity> findByLecCode(String lecCode);
//	
//	@Query(value = "SELECT b FROM BoardEntity b JOIN FETCH b.memberEntity m WHERE b.lecCode IS NOT NULL",
//		       countQuery = "SELECT COUNT(b) FROM BoardEntity b WHERE b.lecCode IS NOT NULL")
//		Page<BoardEntity> findBoardEntitiesWithMemberAndLecCodeNotNull(Pageable pageable);

}
