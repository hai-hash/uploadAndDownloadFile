package com.laptrinhweb;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.laptrinhweb.Sevice.FileStorageService;
import com.laptrinhweb.domain.Document;
import com.laptrinhweb.domain.FileStorageProperties;
import com.laptrinhweb.repository.DocumentRepository;

@Controller
@RequestMapping(path  = "/document")
public class DocumentController {
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	FileStorageService fileStorageService;
	
	
	
	@GetMapping
	public String Home(Model model) {
		List<Document> documents = new ArrayList<Document>();
		documents = documentRepository.findAll();
		model.addAttribute("documents", documents);
		return "index";
	}
	
	@PostMapping("/")
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
//		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String fileName = fileStorageService.storeFile(multipartFile);
		Document document = new Document();
		document.setFileName(fileName);
		document.setSizeFile(multipartFile.getSize());
		document.setUploadTime(new Date());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/document/downloadFile/").path(fileName).toUriString();
		System.out.print(fileDownloadUri);
		document.setFileDownloadUri(fileDownloadUri);
		documentRepository.save(document);
		return "redirect:/document";
		
	}
	
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        	
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
