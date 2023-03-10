package kr.co.ChimAcademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.dao.MainDAO;
import kr.co.ChimAcademy.vo.TestVO;

@Service
public class MainService{

	@Autowired
	private MainDAO dao;
	
	public List<TestVO> selectTest() {
		List<TestVO> list = dao.selectTest();
		return list;
	}

}
