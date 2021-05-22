package com.laptrinhweb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.laptrinhweb.domain.Image;

@Controller
@RequestMapping(path = "/")
public class HomeController {
	
	private RestTemplate rest = new RestTemplate();
	@GetMapping
	public String home(Model model) {
		List<Image> images = new ArrayList<Image>();
		images = Arrays.asList(rest.getForObject("http://localhost:8082/api", Image[].class));
		model.addAttribute("images", images);
		return "file";
	}
	
}
