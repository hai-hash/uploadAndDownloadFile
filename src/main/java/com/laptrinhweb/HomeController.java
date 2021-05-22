package com.laptrinhweb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.laptrinhweb.domain.Image;
import com.laptrinhweb.repository.ImageRepository;

@Controller
@RequestMapping(path = "/")
public class HomeController {
	@Autowired
	private ImageRepository imageRepo;
	
	private RestTemplate rest = new RestTemplate();
	@GetMapping
	public String home(Model model) {
		List<Image> images = new ArrayList<Image>();
//		images = Arrays.asList(rest.getForObject("https://file-managementt.herokuapp.com/api", Image[].class));
		images = imageRepo.findAll();
		model.addAttribute("images", images);
		return "file";
	}
	
}
