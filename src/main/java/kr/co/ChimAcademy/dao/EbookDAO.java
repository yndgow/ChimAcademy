package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.EbookCate1VO;
import kr.co.ChimAcademy.vo.EbookCate2VO;
import kr.co.ChimAcademy.vo.EbookFileVO;
import kr.co.ChimAcademy.vo.EbookVO;

@Mapper
@Repository
public interface EbookDAO {
	public int insertEbook(EbookVO vo);
	public int insertEbookFlie(EbookFileVO vo);
	public List<EbookCate1VO> selectCate1s();
	public List<EbookCate2VO> selectCate2s(int c1);
}
