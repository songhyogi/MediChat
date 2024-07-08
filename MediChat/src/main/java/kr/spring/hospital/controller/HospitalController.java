package kr.spring.hospital.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HospitalController {
	
	@Value("${API.KSY.KAKAO-API-KEY}")
	private String apiKey;
	
	
	@GetMapping("/hospitals")
	public Model hospital(Model model) {
		
		model.addAttribute("apiKey", apiKey);
		return model;
	}
	
	
	@GetMapping("/hospitals/search")
	public String search() {
		return "search";
	}
	
	
}
