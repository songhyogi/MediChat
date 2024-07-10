package kr.spring.schedule.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.schedule.service.ScheduleService2;
import kr.spring.schedule.vo.DayoffVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ScheduleController2 {
	@Autowired
	private ScheduleService2 scheduleService;
	
	/* =====================
	 * 스케줄 목록
	 *=====================*/
	
	@GetMapping("/schedule/list2")
	public String showScheduleList(HttpSession session, Model model) {
		/*
		DoctorVO user = (DoctorVO) session.getAttribute("user");//DoctorController.java에서 확인
		if(user == null) {//로그인이 되지 않은 경우
			return "redirect:/member/login";//로그인 페이지로 리다이렉트
		}
		if(user.getMem_auth()!=3) {//auth가 3이 아닌 경우
			return "main";//에러페이지로 이동 예정
		}
		
		if(user.getDoc_treat()==0) {//처음 스케줄관리서비스를 사용할 경우 휴무일이 있는지 확인
			// 휴무신청 폼을 만들면 수정 예정
			if(user.getDoc_off()==null) {//휴무일이 없는 경우
				model.addAttribute("message","휴무일을 등록해야만 예약서비스를 사용할 수 있습니다.");
				return "";//마이페이지로 이동 예정
			}else if(user.getDoc_off()!=null) {
				model.addAttribute("message", "신청완료");
			}
			
		}
		
		Long doc_num = user.getMem_num();
		String doc_name = user.getMem_name();
		model.addAttribute("doc_num", doc_num);
		model.addAttribute("doc_name",doc_name);
		*/
        return "scheduleList";//타일스
    }
   
    
}
