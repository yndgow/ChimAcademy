package kr.co.ChimAcademy.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.DepartmentVO;

@Mapper
@Repository
public interface AssistantDAO {
	DepartmentVO selectDep(String uid);
}
