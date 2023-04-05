package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.CountVO;
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
	public int selectCountTotal(String sort, @Param("vo")EbookVO vo);
	public int selectCountTotalSearch(String keyword);
	public CountVO selectCountEbooks(String GROUP);
	public List<EbookVO> selectEbooksSearch(String keyword, int start);
	public List<EbookVO> selectEbooks(String sort, String type, @Param("vo")EbookVO vo, int start);
	public EbookVO selectEbook(String bookId);
	public int updateEbookLoan(int sort,String bookId);
	public int updateEbookReserv(int sort,String bookId);
	// 내서재
	public int insertMylib(MylibVO vo);
	public List<MylibVO> selectMylibs(String uid, String state, int start);
	public int selectCountTotalMylibs(String uid, String state);
	public int selectCountForCheckMylib(String uid, String bookId, String state);
	public int selectDateDIFF(int no);
	public EbookFileVO selectEbookFile(String bookId);
	public int updateEbookDown(String bookId);
	public int updateMylibReturn(int no);
	public int updateMylibReturnDate(int no);
	public int updateMylibReservToBorrow(int no);
	public int updateEbookLike(String bookId);
}
