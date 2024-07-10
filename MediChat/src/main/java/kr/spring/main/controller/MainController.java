package kr.spring.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	@GetMapping("/main/main")
	public ModelAndView main(HttpSession session) {
		String lat = (String)session.getAttribute("user_lat")==null ? "정보 없음" : (String)session.getAttribute("user_lat");
		String lon = (String)session.getAttribute("user_lon")==null ? "정보 없음" : (String)session.getAttribute("user_lat");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("main");
		mav.addObject("user_lat", lat);
		mav.addObject("user_lon", lon);
		
		return mav;//Tiles의 설정명
	}
	@PostMapping("/main")
	public String locationInfo(HttpServletRequest request,HttpSession session) {
		//위도 경도 값 세팅
		//위도
		String lat = request.getParameter("user_lat");
		//경도
		String lon = request.getParameter("user_lon");
	
		session.setAttribute("user_lat", lat);
		session.setAttribute("user_lat", lon);
	
		log.debug("<<위치 정보>> : " + lat + "," + lon);
		return "/main/main";
	}
}