package com.laptrinhweb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(path = "/")
public class HomeController {
	@Autowired
	private ImageRepository imageRepo;
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
	public ResponseEntity<Resource> dowload(@RequestParam("id") String id, HttpServletRequest request) {
		System.out.println(id);
		Image image = imageRepo.findOneById(Long.parseLong(id));
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
	
}
