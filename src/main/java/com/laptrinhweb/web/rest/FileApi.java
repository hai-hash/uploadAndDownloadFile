package com.laptrinhweb.web.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import org.apache.logging.log4j.util.Base64Util;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhweb.Exception.MyFileNotFoundException;
import com.laptrinhweb.domain.File1;
import com.laptrinhweb.domain.Image;
import com.laptrinhweb.repository.ImageRepository;
import com.laptrinhweb.util.FileUtils;



@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class FileApi {
	@Autowired
	private FileUtils fileUtil;
	@Autowired
	private ImageRepository imageRepo;
//	@Autowired
//	private IFileService fileService;
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private userRepository userRepository;
//	@PostMapping
//	public ResponseEntity<File> createNewFile(@RequestBody File file){
//		userService.getAll();
//		User user = new User();
//		user.setId(1L);
//		user.setUserName("hai");
//		user.setPassWord("123456");
//		userRepository.save(user);
//		return new ResponseEntity<>(fileService.save(file),HttpStatus.OK);
//	}
//	
//	@GetMapping
//	public ResponseEntity<Iterable<File>> getAllFile(){
//		return new ResponseEntity<>(fileService.findAll(),HttpStatus.OK);
//	}
//	@GetMapping("/{id}")
//	public ResponseEntity<File> getFile(@PathVariable Long id){
//		Optional<File> fileOptional = fileService.findById(id);
//		return fileOptional.map(file -> new ResponseEntity<>(file,HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.FOUND));
//		
//				
//				
//	}
//	@PutMapping("/{id}")
//	public ResponseEntity<File> updateFile(@PathVariable("id") Long id,@RequestBody File file){
//		Optional<File> fileOpitional = fileService.findById(id);
//		return fileOpitional.map(file1 ->{
//			file.setId(id);
//			return new ResponseEntity<>(fileService.save(file),HttpStatus.OK);
//		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<File> removeFile(@PathVariable("id") Long id){
//		Optional<File> fileOptional = fileService.findById(id);
//		return fileOptional.map(file ->{
//			fileService.remove(id);
//			return new ResponseEntity<>(file,HttpStatus.OK);
//		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//	}
	
	@PostMapping(value = "/updatefile")
	public ResponseEntity<Void> updateFile(@RequestBody File1 file1){
		System.out.print("hello , tôi đã vào");
		byte[] base64 = Base64.getDecoder().decode(file1.getBase64().getBytes());
		fileUtil.updateOrWrite(base64, "/upload/"+ file1.getFileName(),file1.getFileName());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/download/image/{id}")
	public ResponseEntity<Resource> dowload(@PathVariable(value = "id") Long id, HttpServletRequest request) {
		System.out.println(id);
		Image image = imageRepo.findOneById(id);
		try {

			String filePath1 = image.getFilePath();
			Path filePath = Paths.get(filePath1);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				String contentType = null;
				try {
					contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
					System.out.println(resource.getFile().getAbsolutePath());
					
				} catch (IOException ex) {

				}

				if (contentType == null) {
					contentType = "application/octet-stream";
				}
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
			} else {
				throw new MyFileNotFoundException("File not found " + image.getNameFile());
			}
		} catch (MalformedURLException e) {

			throw new MyFileNotFoundException("File not found " + image.getNameFile(), e);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<List<Image>> getAllImage(){
		return new ResponseEntity<>(imageRepo.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Image> getById(@PathVariable(value = "id") Long id){
		Image image = imageRepo.findOneById(id);
		return new ResponseEntity<>(image,HttpStatus.OK);
	}
	
	

}
