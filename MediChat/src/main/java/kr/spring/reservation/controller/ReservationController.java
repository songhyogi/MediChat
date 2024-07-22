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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.member.vo.MemberVO;
import kr.spring.reservation.service.ReservationService;
import kr.spring.reservation.vo.ReservationVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@ModelAttribute
	public ReservationVO initCommand() {
		return new ReservationVO();
	}
	
	@GetMapping("/reservation/myResList")
	public String getMyResList (@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") String keyfield, HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mem_num", user.getMem_num());
			int count = reservationService.selectCountByMem(map);
			map.put("keyfield", keyfield);
			PagingUtil page = new PagingUtil(keyfield,null,pageNum,count,5,10,"myResList");
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			List<ReservationVO> list = reservationService.getMyResList(map);
			
			model.addAttribute("count",count);
			model.addAttribute("list",list);
			model.addAttribute("page",page.getPage());
			
			return "myReservationList";
		}
		return "common/resultAlert";
	}
}
