package com.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.DocumentDTO;
import com.spring.dto.UserDTO;

public interface DocumentService {
	
//	public List<DocumentDTO> getAllDocuments();
	
	// 문서 조회
	public DocumentDTO getDocumentByDocumentNo(Long documentNo);

	// 문서 작성
	public void insertDocument(DocumentDTO documentDTO, MultipartFile multipartFile);
	
	// 문서 수정
	public void updateDocument(Long documentNo, DocumentDTO documentDTO);
	
	// 문서 삭제
	public void deleteDocumentByDocumentNo(Long documentNo);
	
}
