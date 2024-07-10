package kr.spring.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@GetMapping("/")
	public String init() {
		return "redirect:/main";
	}
	
	@GetMapping("/main")
	public String main() {
		
		return "main";//Tiles의 설정명
	}
	
	@PostMapping("/locationInfo")
	public String locationInfo(HttpServletRequest request,HttpSession session) {
		//위도 경도 값 세팅
		//위도
		String lat = request.getParameter("user_lat");
		//경도
		String lon = request.getParameter("user_lon");
		
		session.setAttribute("user_lat", lat);
		session.setAttribute("user_lat", lon);
		
		log.debug("<<위치 정보>> : " + lat + "," + lon);
		return "redirect:/";
	}
}








