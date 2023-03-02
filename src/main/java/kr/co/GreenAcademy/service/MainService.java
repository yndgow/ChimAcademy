package kr.co.GreenAcademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.GreenAcademy.dao.MainDAO;
import kr.co.GreenAcademy.vo.TestVO;

@Service
public class MainService{

	@Autowired
	private MainDAO dao;
	
	public List<TestVO> selectTest() {
		List<TestVO> list = dao.selectTest();
		return list;
	}

}
