package com.spring.service;

import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.DocumentDTO;
import com.spring.dto.PageRequestDTO;
import com.spring.dto.PageResultDTO;
import com.spring.dto.UserDTO;
import com.spring.entity.Document;
import com.spring.mapper.UserMapper;
import com.spring.repository.DocumentRepository;
import com.spring.util.S3Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{
	
	private final DocumentRepository documentRepository;
	
//	@Override
//	public List<DocumentDTO> getAllDocuments() {
//		List<Document> documents = documentRepository.findAll();
//		List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
//		for (Document document : documents) {
//			documentDTOs.add(document.toDTO(document));
//		}
//		return documentDTOs;
//	}
	
//	public PageResultDTO<DocumentDTO, Document> getList(PageRequestDTO pageRequestDTO) {
//		Pageable pageable = pageRequestDTO.getPageable(Sort.by("documentNo").descending());
//		
//		Page<Document> result = documentRepository.findAll(pageable);
//		
//		// entity -> dto
//		Function<Document, DocumentDTO> function = (Document -> Document.toDTO(Document));
//		
//		return new PageResultDTO<DocumentDTO, Document>(result, function);
//	}
	
	
	// 문서 조회
	@Override
	public DocumentDTO getDocumentByDocumentNo(Long documentNo) {
		Document document = documentRepository.findDocumentByDocumentNo(documentNo);
		return document == null ? null : document.toDTO(document);
	}
	
	// 문서 작성
	@Override
	public void insertDocument(DocumentDTO documentDTO ,MultipartFile multipart) {		
			String originalFileName =  multipart.getOriginalFilename();
			String filename = UUID.randomUUID().toString() + "_" + originalFileName;	
			try {
				S3Util.uploadFile(filename, multipart.getInputStream());
			} catch (Exception e) {
				 System.out.println(e.getMessage());
			}
			documentDTO.setOriginalName(originalFileName);
			documentDTO.setFileName(filename);
			
			documentRepository.save(documentDTO.toEntity(documentDTO));
		}	
	
	// 문서 수정
	@Override
	public void updateDocument(Long documentNo,DocumentDTO documentDTO) {
		documentDTO.setDocumentNo(documentNo);
		DocumentDTO orginalDTO = getDocumentByDocumentNo(documentDTO.getDocumentNo());
		if(orginalDTO != null) {
			DocumentDTO newDTO = new DocumentDTO(orginalDTO, documentDTO);
			documentRepository.save(newDTO.toEntity(newDTO));
		}
	}
	
	// 문서 삭제
	@Override
	public void deleteDocumentByDocumentNo(Long documentNo) {
		documentRepository.deleteDocumentByDocumentNo(documentNo);
	}
}
