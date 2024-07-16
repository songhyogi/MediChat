package kr.spring.drug.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.drug.service.MemberDrugService;
import kr.spring.drug.vo.DrugInfoVO;
import kr.spring.drug.vo.MemberDrugVO;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberDrugController {
	@Autowired
	MemberDrugService memberDrugService;
	
	/*------회원 의약품 목록------*/
	//마이페이지 접근
	@GetMapping("/memberDrug/list")
	public String getDrugList(HttpSession session, Model model) {
				
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		if(user == null) {
			return "redirect:/member/login";
		}
		
		List<MemberDrugVO> memberDrug = memberDrugService.selectMemberDrugList(user.getMem_num());
		
		log.debug("<<회원 의약품 목록>> : " + memberDrug);
		
		model.addAttribute("memberDrug", memberDrug);
		
		return "memberDrugList";
	}
	
	/*------회원 의약품 등록------*/
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
	
	//의약품 등록
	@GetMapping("/memberDrug/memberDrugInsertAjax")
	@ResponseBody
	public Map<String, Object> memberDrugInsertAjax(MemberDrugVO memberDrug, HttpSession session){
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		
		if(user == null) {
			mapJson.put("result", "logout");
		}
		
		log.debug("<<의약품 등록>> : " + memberDrug);
		
		memberDrug.setMem_num(user.getMem_num());
		
		memberDrugService.insertDrug(memberDrug);
		
		mapJson.put("result", "success");
		
		return mapJson;
	}
	
	/*------회원 의약품 수정------*/
	/*------회원 의약품 삭제------*/
}
