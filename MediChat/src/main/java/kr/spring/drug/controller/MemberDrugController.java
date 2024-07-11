package kr.spring.drug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.drug.service.MemberDrugService;

@Controller
public class MemberDrugController {
	@Autowired
	MemberDrugService memberDrugService;
	
	/*------회원 의약품 목록------*/
	@GetMapping("/memberDrug/list")
	public String getDrugList() {
		
		
		return "memberDrugList";
	}
}
