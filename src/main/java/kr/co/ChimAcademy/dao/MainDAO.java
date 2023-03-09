package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.TestVO;

@Repository
@Mapper
public interface MainDAO {

	List<TestVO> selectTest();
}
