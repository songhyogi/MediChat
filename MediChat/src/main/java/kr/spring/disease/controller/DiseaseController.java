package kr.spring.disease.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kr.spring.disease.service.DiseaseService;
import kr.spring.disease.vo.Item;
import kr.spring.disease.vo.Response;
import kr.spring.util.XUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DiseaseController {
	
	@Autowired
	private DiseaseService service;
	
	@GetMapping("/disease/diseasemain")
	public String getList(
			 @RequestParam(defaultValue="1") int pageNum,
			 @RequestParam(defaultValue="1") int order,
			 @RequestParam(defaultValue="") String category,
			 String keyfield,String keyword,Model model,HttpServletRequest request) throws JAXBException, IOException {
		
		log.debug("<<게시판 목록 - category>> : " + category);
		category = "03";
		Response smart = XUtil.getDetailList(request,category);
		log.debug(">>>>>>>>>>>>>"+smart);
		Item item = new Item();
		item.setTitle(smart.getBody().getItemList().getIList().get(0).getTitle());
		item.setMessage(smart.getBody().getItemList().getIList().get(0).getMessage().toString());
		String state1 = item.getMessage().substring(item.getMessage().indexOf("증상은"),item.getMessage().indexOf("치료는"));
		String state2 = state1.substring(state1.indexOf("?")+1,state1.indexOf("▶"));
		
		Response depart =XUtil.getDiseaseDepart(request, category);
		
	
		model.addAttribute("smart",smart);
		model.addAttribute("m",state2);
		model.addAttribute("depart",depart);
		return "diseaseMain";
	}

	
}
