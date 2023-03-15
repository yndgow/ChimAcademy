package kr.co.ChimAcademy.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.ChimAcademy.dao.EbookDAO;
import kr.co.ChimAcademy.vo.EbookCate1VO;
import kr.co.ChimAcademy.vo.EbookCate2VO;
import kr.co.ChimAcademy.vo.EbookFileVO;
import kr.co.ChimAcademy.vo.EbookVO;
import kr.co.ChimAcademy.vo.Ebook_ArticleVO;
import kr.co.ChimAcademy.vo.Ebook_Article_fileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EbookService {
	
	@Autowired
	private EbookDAO dao;
	
	public List<EbookCate1VO> selectCate1s(){
		return dao.selectCate1s();
	};
	public List<EbookCate2VO> selectCate2s(int c1){
		return dao.selectCate2s(c1);
	};
	
	public int insertEbook(EbookVO vo) {
		// 책파일 업로드
		List<EbookFileVO> fvos = fileUpload(vo);
		// 책등록
		EbookFileVO thumb = fvos.get(1);
		String thumbLocation = "/elibFile/thumbFile/" + thumb.getNewName();
		vo.setThumb(thumbLocation);
		int result = dao.insertEbook(vo);
		// 책파일 등록
		EbookFileVO epub = fvos.get(0);
		if(epub != null) {
			dao.insertEbookFlie(epub);
		}
		return result;
	};
	
	// 책파일 업로드 /////////////////////////////////////////

	private String EbookUploadPath = "elibFile/ebookFile/"; // 프로젝트 내 가상 경로
	private String ThumbUploadPath = "elibFile/thumbFile/"; // 프로젝트 내 가상 경로
	
	public List<EbookFileVO> fileUpload(EbookVO vo) {
		// 책 파일 첨부
		MultipartFile epub = vo.getFname();
		// 썸네일 파일 첨부 
		MultipartFile thumb = vo.getTname();
		List<MultipartFile> files = new ArrayList<>();
		files.add(epub);
		files.add(thumb);
		List<EbookFileVO> fvos = new ArrayList<>();
		int idx = 0;
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				// 새 파일명 생성
				String oName = file.getOriginalFilename();
				String ext = oName.substring(oName.lastIndexOf("."));
				String nName = UUID.randomUUID().toString()+ext;
				String path = null;
				// 시스템 경로
				if(idx == 0) {
					path = new File(EbookUploadPath).getAbsolutePath();
				}else {
					path = new File(ThumbUploadPath).getAbsolutePath();
				}
				
				// 파일 저장
				try {
					file.transferTo(new File(path, nName));
				} catch (IllegalStateException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
				
				EbookFileVO fvo = EbookFileVO.builder()
												.parent(vo.getBookId())
												.oriName(oName)
												.newName(nName)
												.build();
				fvos.add(fvo);
				idx++;
			}
		}
		return fvos;
	}
}
