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
	public String main(HttpSession session) {
		return "main";
	}
}