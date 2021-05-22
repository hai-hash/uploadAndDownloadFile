package com.laptrinhweb.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.laptrinhweb.domain.Image;

@Controller
@RequestMapping("/image")
public class ImageController {
	private RestTemplate rest = new RestTemplate();
	
	@GetMapping
	public String getAll(Model model) {
		List<Image> images = new ArrayList<Image>();
		images = Arrays.asList(rest.getForObject("https://file-managementt.herokuapp.com/api", Image[].class));
		model.addAttribute("images", images);
		return "file";
		
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadImage(@RequestParam("id") String id,HttpServletRequest request) {
		ResponseEntity<Resource> re = rest.getForEntity("https://file-managementt.herokuapp.com/api/download/image/{id}", Resource.class,Long.parseLong(id));
		return re;
	}

}
