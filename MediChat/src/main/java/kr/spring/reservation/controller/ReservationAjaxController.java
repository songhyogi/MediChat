package kr.spring.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationAjaxController {
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/reservation/hosHours")
	@ResponseBody
	public HospitalVO getHosHours(Long hos_num, HttpSession session) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		if (user == null) {
			throw new RuntimeException("로그인이 필요합니다.");
		}

		return reservationService.getHosHours(hos_num);
	}
}
