package kr.spring.schedule.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.doctor.vo.DoctorVO;
import kr.spring.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ScheduleAjaxController {
	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping("/schedule/workingTimes")
	@ResponseBody
	public Map<String, Object> getWorkingTimes(Long doc_num,HttpSession session){
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(user == null) {
			map.put("result", "logout");
		}else if(user.getMem_auth()!=3 || user.getDoc_treat()==0){
			map.put("result", "wrongAccess");
		}else {
			Map<String,String> workingHours = scheduleService.getWorkingHours(doc_num);
			map.put("result", "success");
			map.put("workingHours", workingHours);
		}
		return map;
	}
}
