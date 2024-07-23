package kr.spring.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.health.service.HealthyService;
import kr.spring.health.vo.HealthyBlogVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@Autowired
	private HealthyService service;
	
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	@GetMapping("/main/main")
	public String main(HttpSession session,Model model) {
		Map<String,Object> map =new HashMap<String,Object>();
		
		int count = service.selectHealCount(map);
		
		PagingUtil page = new PagingUtil(1,count,4,10,null);
		
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		
		List<HealthyBlogVO> list = service.selectHealList(map);
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		
		return "main";
	}
}