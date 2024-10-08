package kr.spring.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.consulting.service.ConsultingService;
import kr.spring.consulting.vo.ConsultingVO;
import kr.spring.health.service.HealthyService;
import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@Autowired
	private HealthyService healthyService;
	
	@Autowired
	private ConsultingService consultingService;
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	@GetMapping("/main/main")
	public String main(HttpSession session,Model model) {
		Map<String,Object> map =new HashMap<String,Object>();
		
		int hCount = healthyService.selectHealCount(map);
		
		PagingUtil page = new PagingUtil(1,hCount,4,10,null);
		
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		
		List<HealthyBlogVO> hList = healthyService.selectHealList(map);
		
		model.addAttribute("hList",hList);
		model.addAttribute("hCount",hCount);
		model.addAttribute("page",page.getPage());
		
		
		map.put("pageNum", "1");
		map.put("pageItemNum", "4");
		map.put("con_type", "0");
		List<ConsultingVO> cList = consultingService.getListConsulting(map);
		model.addAttribute("cList", cList);
		return "main";
	}
}