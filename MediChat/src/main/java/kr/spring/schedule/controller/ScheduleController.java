package kr.spring.schedule.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ScheduleController {
	@GetMapping("/schedule/list")
	public String showScheduleList(HttpSession session, Model model) {
		
		return "scheduleList";//타일스
	}
}
