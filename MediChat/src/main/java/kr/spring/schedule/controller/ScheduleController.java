package kr.spring.schedule.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.holiday.vo.HolidayVO;
import kr.spring.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	
	/* =====================
	 * 스케줄 관리
	 *=====================*/
	@GetMapping("/schedule/list")
	public String showScheduleList(HttpSession session, Model model) {
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         return "/common/resultAlert";
	    }
		if(user.getMem_auth()!=3) {//auth가 3이 아닌 경우
			model.addAttribute("message","잘못된 접근입니다.");
			model.addAttribute("url","/main/main");
			return "/common/resultAlert";//에러페이지로 이동
		}
		if(user.getDoc_treat()!=1) {
			model.addAttribute("message", "스케줄관리를 이용하시려면 예약서비스 신청을 먼저 해야 합니다.");
			model.addAttribute("url", "/doctor/registerTreat");
			return "/common/resultAlert";
		}
		
		Long doc_num = user.getMem_num();
		String doc_name = user.getMem_name();
		
		String regularDayOff = scheduleService.getRegularDayoff(doc_num);
		model.addAttribute("doc_num", doc_num);
		model.addAttribute("doc_name",doc_name);
		model.addAttribute("regularDayOff", regularDayOff);
		
        return "scheduleList";
    }
}
