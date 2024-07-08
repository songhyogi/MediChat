package kr.spring.schedule.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.member.vo.MemberVO;
import kr.spring.schedule.service.ScheduleService;
import kr.spring.schedule.vo.DayoffVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	
	/* =====================
	 * 스케줄 목록
	 *=====================*/
	
	@GetMapping("/schedule/list")
	public String showScheduleList(HttpSession session, Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			return "redirect:/member/login";//로그인 페이지로 리다이렉트
		}
		Long doc_num = user.getMem_num();
		String doc_name = user.getMem_name();
		model.addAttribute("doc_num", doc_num);
		model.addAttribute("doc_name",doc_name);
        return "scheduleList";//타일스 예정
    }
   
    
}
