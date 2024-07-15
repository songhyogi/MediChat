package kr.spring.reservation.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationController {
	
	@GetMapping("/reservation/reservation/{hos_num}")
	public String reservation(@PathVariable Long hos_num,Model model) {
		model.addAttribute("hos_num", hos_num);
		
		return "reservation/reservation";
	}
}
