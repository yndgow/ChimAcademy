package kr.co.ChimAcademy.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ChimAcademy.dao.Ebook_ArticleDAO;
import kr.co.ChimAcademy.vo.EbookFileVO;
import kr.co.ChimAcademy.vo.Ebook_ArticleVO;
import kr.co.ChimAcademy.vo.Ebook_Article_fileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Ebook_ArticleService {

	@Autowired
	private Ebook_ArticleDAO dao;
	
	public int insertArticle(Ebook_ArticleVO vo) {
		
		// 글 등록
		int result = dao.insertArticle(vo);
		// 파일 업로드
		Ebook_Article_fileVO fvo = fileUpload(vo);
		// 파일 등록
		if(fvo != null) {
			dao.insertFile(fvo);
		}
		
		return result;	
	};
	public Ebook_ArticleVO selectArticle(int no) {
		return dao.selectArticle(no);
	};
	public List<Ebook_ArticleVO> selectArticles(int start) {
		return dao.selectArticles(start);
	};
	public List<Ebook_ArticleVO> selectNotices(){
		return dao.selectNotices();
	};
	public Ebook_Article_fileVO selectFile(int fno) {
		return dao.selectFile(fno);
	};
	public int updateFileDownload(int fno) {
		return dao.updateFileDownload(fno);
	};
	public void updateArticle(Ebook_ArticleVO vo) {
		dao.updateArticle(vo);
	};
	public void updateArticleHit(int no) {
		dao.updateArticleHit(no);
	};
	public void deleteArticle(int no) {
		dao.deleteArticle(no);
	};
	// 파일 다운로드 //////////////////////////////////////////////////////
	public ResponseEntity<Resource> fileDownload(Ebook_Article_fileVO vo) throws IOException {
		// String path = new File(uploadPath).getAbsolutePath()+"/"+vo.getNewName();
		Path path = Paths.get(uploadPath+vo.getNewName());
		String contentType = Files.probeContentType(path);
		HttpHeaders headers = new HttpHeaders(); // 스프링에서 제공 : HttpHeaders, ResponseEntity
		headers.setContentDisposition(ContentDisposition
														.builder("attachment")
														.filename(vo.getOriName(), StandardCharsets.UTF_8)
														.build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK); // res 객체
	}

	// 파일 업로드 ///////////////////////////////////////////////////////
	
	private String uploadPath = "elibFile/articleFile/"; // 프로젝트 내 가상 경로
	
	public Ebook_Article_fileVO fileUpload(Ebook_ArticleVO vo) {
		// 첨부 파일
				MultipartFile file = vo.getFname();
				Ebook_Article_fileVO fvo = null;
				
				if(!file.isEmpty()) {
					// 시스템 경로
					String path = new File(uploadPath).getAbsolutePath();
					
					// 새 파일명 생성
					String oName = file.getOriginalFilename();
					String ext = oName.substring(oName.lastIndexOf("."));
					String nName = UUID.randomUUID().toString()+ext;
					
					// 파일 저장
					try {
						file.transferTo(new File(path, nName));
					} catch (IllegalStateException e) {
						log.error(e.getMessage());
					} catch (IOException e) {
						log.error(e.getMessage());
					}
					
					fvo = Ebook_Article_fileVO.builder()
							.parent(vo.getNo())
							.oriName(oName)
							.newName(nName)
							.build();
				}
				
				return fvo;
	}
	// 파일삭제  ////////////////////////////////////////////////////////////
	@Transactional
	public Ebook_Article_fileVO deleteFile(int no) {
		Ebook_Article_fileVO vo = dao.selectFileByParent(no);
		dao.deleteFile(no);
		return vo;
	};
	public void deleteRealFile(Ebook_Article_fileVO vo) {
		if(vo.getNewName() != null) {
			// 실제 파일 삭제
			String path = new File("elibFile/articleFile/").getAbsolutePath();
			File file = new File(path,vo.getNewName());
			if(file.exists()) {
				file.delete();
			}
		}
	}
	// 페이징 처리 시작 ///////////////////////////////////////////////////////
	// 현재 페이지 번호
	public int getCurrnetPage(String pg) {
		int currentPage = 1;
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage-1) * 10;
	}
	// 게시물 총 갯수
	public int selectCountTotal() {
		return dao.selectCountTotal();
	}
	// 마지막 페이지 번호
	public int getLastPageNum(int total) {
		int lastPageNum = 0;
		if(total % 10 == 0){
			lastPageNum = total / 10;
		}else{
			lastPageNum = total / 10 + 1;
		}
		return lastPageNum;
	}
	// 시작 페이지 번호
	public int getPageStartNum(int total, int start) {
		return total - start;
	}
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPageNum) {
		int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
		int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
		int pageGroupEnd = currentPageGroup * 10;
		if(pageGroupEnd > lastPageNum){
			pageGroupEnd = lastPageNum;
		}
		int[] result = {pageGroupStart,pageGroupEnd};
		return result;
	}
	// 페이징 처리 끝 ///////////////////////////////////////////////////////
	
}
