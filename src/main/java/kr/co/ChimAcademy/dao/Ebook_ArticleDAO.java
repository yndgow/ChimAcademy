package kr.co.ChimAcademy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.ChimAcademy.vo.Ebook_ArticleVO;
import kr.co.ChimAcademy.vo.Ebook_Article_fileVO;

@Mapper
@Repository
public interface Ebook_ArticleDAO {
	public int insertArticle(Ebook_ArticleVO vo); // int는 실행 결과의 raw 갯수 
	public int insertFile(Ebook_Article_fileVO vo);
	public Ebook_ArticleVO selectArticle(int no);
	public List<Ebook_ArticleVO> selectArticles(int start);
	public List<Ebook_ArticleVO> selectNotices();
	public Ebook_Article_fileVO selectFile(int fno);
	public int updateFileDownload(int fno);
	public void updateArticle(Ebook_ArticleVO vo);
	public void updateArticleHit(int no);
	public void deleteArticle(int no);
	public int selectCountTotal();
	public Ebook_Article_fileVO selectFileByParent(int no);
	public int deleteFile(int no);
}
