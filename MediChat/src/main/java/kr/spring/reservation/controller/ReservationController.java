package kr.spring.reservation.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationController {

	@GetMapping("/reservation/reservation")
	@ResponseBody
	public Map<String,String> reservation(Long hos_num,Model model,HttpSession session) {
		log.debug("<<ajax 컨트롤러 진입>>");
		Map<String,String> map = new HashMap<>();
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {
			map.put("result","success");
			model.addAttribute("hos_num", hos_num);
		}
		return map;
	}
}
