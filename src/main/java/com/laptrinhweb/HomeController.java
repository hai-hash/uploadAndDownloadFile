package com.laptrinhweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.laptrinhweb.Exception.MyFileNotFoundException;
import com.laptrinhweb.domain.Image;
import com.laptrinhweb.repository.ImageRepository;
import com.laptrinhweb.util.MediaTypeUtils;

@Controller
@RequestMapping(path = "/")
public class HomeController {
	@Autowired
	private ImageRepository imageRepo;
	
	@Autowired
    private ServletContext servletContext;
	
	
	@GetMapping
	public String home(Model model) {
		String path = System.getProperty("user.dir");
		System.out.print(path);
		List<Image> images = new ArrayList<Image>();
		images = imageRepo.findAll();
		model.addAttribute("images", images);
		return "file";
	}
	
	
	@GetMapping(value = "/download/image")
	public ResponseEntity<InputStreamResource> dowload(@RequestParam("id") String id, HttpServletRequest request) throws FileNotFoundException {
		System.out.println(id);
		Image image = imageRepo.findOneById(Long.parseLong(id));
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, image.getNameFile());
		 File file = new File(image.getFilePath());
		 InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		 
		 return ResponseEntity.ok()
	                // Content-Disposition
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
	                // Content-Type
	                .contentType(mediaType)
	                // Contet-Length
	                .contentLength(file.length()) //
	                .body(resource);
	}
	
}
