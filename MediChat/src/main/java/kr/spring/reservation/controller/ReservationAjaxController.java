package kr.spring.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.reservation.service.ReservationService;
import kr.spring.reservation.vo.ReservationVO;
import kr.spring.review.vo.ReviewVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationAjaxController {
	@Autowired
	private ReservationService reservationService;
	
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

	@GetMapping("/reservation/hosHours")
	@ResponseBody
	public Map<String,Object> getHosHours(Long hos_num, HttpSession session) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		if (user == null) {
			map.put("result", "logout");
		}else {
			HospitalVO hospitalVO = reservationService.getHosHours(hos_num);
			map.put("result", "success");
			map.put("hospitalVO", hospitalVO);
		}

		return map;
	}
	
	@GetMapping("/reservation/availableDoctor")
    @ResponseBody
    public Map<String, Object> getAvailableDoctors(@RequestParam Long hos_num, 
                                                   @RequestParam String date,
                                                   @RequestParam String time, 
                                                   @RequestParam int dayOfWeek, 
                                                   HttpSession session) {
        MemberVO user = (MemberVO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("result", "logout");
        } else {
        	log.debug("Received hos_num: " + hos_num);
        	log.debug("Received date: " + date);
        	log.debug("Received time: " + time);
        	log.debug("Received dayOfWeek: " + dayOfWeek);
            Map<String, Object> params = new HashMap<>();
            params.put("hos_num", hos_num);
            params.put("date", date);
            params.put("time", time);
            params.put("dayOfWeek", dayOfWeek);
            List<DoctorVO> doctors = reservationService.getAvailableDoctors(params);
            map.put("result", "success");
            map.put("doctors", doctors);
            log.debug("Doctors: " + doctors); // 추가한 로그
        }
        return map;
    }
	
	@PostMapping("/reservation/reservationcompleted")
	@ResponseBody
	public Map<String, Object> submitReservation(@RequestBody Map<String, Object> reservationData, HttpSession session) {
	    Map<String, Object> map = new HashMap<>();
	    MemberVO user = (MemberVO) session.getAttribute("user");

	    if (user == null) {
	        map.put("result", "logout");
	    } else {
	        // 로그 추가
	        System.out.println("Received Reservation Data: " + reservationData);

	        ReservationVO reservationVO = new ReservationVO();
	        reservationVO.setMem_num(user.getMem_num());
	        reservationVO.setDoc_num(Long.parseLong(reservationData.get("doc_num").toString()));
	        reservationVO.setRes_type(Long.parseLong(reservationData.get("res_type").toString()));
	        reservationVO.setRes_date(reservationData.get("res_date").toString());
	        reservationVO.setRes_time(reservationData.get("res_time").toString());
	        reservationVO.setRes_content(reservationData.get("res_content").toString());

	        // 로그 추가
	        System.out.println("Mapped Reservation Data: " + reservationVO);

	        try {
	            reservationService.insertReservation(reservationVO);
	            map.put("result", "success");
	        } catch (Exception e) {
	            e.printStackTrace();
	            map.put("result", "error");
	        }
	    }

	    return map;
	}

	@PostMapping("/reservation/cancelReservation")
	@ResponseBody
	public Map<String,Object> cancelReservation(long res_num,HttpSession session,Model model) {
		Map<String, Object> map = new HashMap<>();
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {
			reservationService.cancelReservation(res_num);
			map.put("result", "success");
		}
		return map;
	}
	
	@PostMapping("/reservation/updateReservation")
	@ResponseBody
	public Map<String,Object> updateReservation(long res_num,long res_status,HttpSession session,Model model){
		Map<String,Object> map = new HashMap<>();
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {
			reservationService.updateReservation(res_num,res_status);
			map.put("result", "success");
		}
		return map;
	}
}
	

