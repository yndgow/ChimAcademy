package kr.co.GreenAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.GreenAcademy.vo.TestVO;

@Repository
@Mapper
public interface MainDAO {

	List<TestVO> selectTest();
}
