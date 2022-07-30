package com.spring.api;

import java.sql.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.DocumentDTO;
import com.spring.dto.PageRequestDTO;
import com.spring.dto.PageResultDTO;
import com.spring.dto.UserDTO;
import com.spring.entity.Document;
import com.spring.service.DocumentServiceImpl;
import com.spring.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class DocumentController {
	
	private final DocumentServiceImpl documentService;
	
	// 문서 조회
	@GetMapping(value = "/document/{documentNo}")
	public DocumentDTO sellectDocument(@PathVariable Long documentNo){
		return documentService.getDocumentByDocumentNo(documentNo);
	}
	
	// 문서 작성
	@PostMapping(value = "/document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void insertDocument(@RequestPart("documentDTO") DocumentDTO documentDTO, @RequestPart("file") MultipartFile multipart) {
		documentService.insertDocument(documentDTO, multipart);	
	}
	
	// 문서 수정
	@PostMapping(value="/document/{documentNo}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateDocument(@PathVariable Long documentNo, @RequestBody DocumentDTO documentDTO) {
		documentService.updateDocument(documentNo, documentDTO);
	}
	
	// 문서 삭제
	@DeleteMapping(value = "/document/{documentNo}")
	public void deleteDocument(@PathVariable Long documentNo) {
		documentService.deleteDocumentByDocumentNo(documentNo);
	}
}
