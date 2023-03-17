package kr.co.ChimAcademy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.ChimAcademy.entity.BoardEntity;

public interface BoardRepo extends JpaRepository<BoardEntity, Integer>{
	Page<BoardEntity> findByLecCodeNotNull(Pageable pageable);
//	
//	
//	List<BoardEntity> findByLecCode(String lecCode);
//	
//	@Query(value = "SELECT b FROM BoardEntity b JOIN FETCH b.memberEntity m WHERE b.lecCode IS NOT NULL",
//		       countQuery = "SELECT COUNT(b) FROM BoardEntity b WHERE b.lecCode IS NOT NULL")
//		Page<BoardEntity> findBoardEntitiesWithMemberAndLecCodeNotNull(Pageable pageable);

}
