package kr.spring.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HospitalController {
	
	@GetMapping("/hospitals")
	public String hospital() {
		
		return "hospitals";
	}
}
