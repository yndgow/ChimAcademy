package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.EbookCate1VO;
import kr.co.ChimAcademy.vo.EbookCate2VO;
import kr.co.ChimAcademy.vo.EbookFileVO;
import kr.co.ChimAcademy.vo.EbookVO;
import kr.co.ChimAcademy.vo.MylibVO;

@Mapper
@Repository
public interface EbookDAO {
	// 전자도서
	public int insertEbook(EbookVO vo);
	public int insertEbookFile(EbookFileVO vo);
	public List<EbookCate1VO> selectCate1s();
	public List<EbookCate2VO> selectCate2s(int c1);
	public int selectCountEbook(EbookVO vo);
	public List<EbookVO> selectEbooks();
	public EbookVO selectEbook(String bookId);
	
	// 내서재
	public int insertMylib(MylibVO vo);
	public List<MylibVO> selectMylibs(String uid);
}
