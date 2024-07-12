package kr.spring.drug.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.drug.service.MemberDrugService;
import kr.spring.drug.vo.DrugInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberDrugController {
	@Autowired
	MemberDrugService memberDrugService;
	
	/*------회원 의약품 목록------*/
	@GetMapping("/memberDrug/list")
	public String getDrugList() {
		
		
		return "memberDrugList";
	}
	
	//의약품 목록 검색
	@GetMapping("/memberDrug/memberDrugSearchAjax")
	@ResponseBody
	public Map<String, Object> memberDrugSearchAjax(String drg_name){
				
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		List<DrugInfoVO> drugList = memberDrugService.selectDrugList(drg_name);
		
		
		mapJson.put("result", "success");
		mapJson.put("drugList", drugList);
		
		
		return mapJson;
	}
}
