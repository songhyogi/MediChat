package kr.spring.schedule.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.doctor.vo.DoctorVO;
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
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if(user == null) {//로그인이 되지 않은 경우
			return "redirect:/doctor/login";//로그인 페이지로 리다이렉트
		}
		if(user.getMem_auth()!=3) {//auth가 3이 아닌 경우
			return "/errors/404";//에러페이지로 이동
		}
		if(user.getDoc_treat()!=1) {
			return "/member/docTreatRegister";
		}
		
		Long doc_num = user.getMem_num();
		String doc_name = user.getMem_name();
		String regularDayOff = scheduleService.getRegularDayoff(doc_num);
		model.addAttribute("doc_num", doc_num);
		model.addAttribute("doc_name",doc_name);
		model.addAttribute("regularDayOff", regularDayOff);
		
        return "scheduleList";//타일스
    }
   
    
}
