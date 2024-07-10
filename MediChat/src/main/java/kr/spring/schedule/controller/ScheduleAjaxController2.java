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
import kr.spring.schedule.service.ScheduleService2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ScheduleAjaxController2 {
	@Autowired
	private ScheduleService2 scheduleService;
	
	@GetMapping("/schedule/dayoffs")
    @ResponseBody
    public Map<String, Object> getDayoffs(HttpSession session,Long doc_num,String doff_date) {
		Map<String,Object> map = new HashMap<String,Object>();
        DoctorVO user = (DoctorVO)session.getAttribute("user");//DoctorController.java 확인 필요

        if(user == null) {
            map.put("result", "logout");
        }else if(user.getMem_num() != doc_num){
        	map.put("result", "wrongAccess");
        }else {
            List<String> times = scheduleService.getDayoffTimes(doc_num,doff_date);
            map.put("result", "success");
            map.put("times", times);
        }
        return map;
    }
	

    @PostMapping("/schedule/update")
    @ResponseBody
    public Map<String, Object> updateDayoffTimes(HttpSession session,Long doc_num,String doff_date,List<String> timesToAdd,List<String> timesToRemove) {
        Map<String, Object> map = new HashMap<>();
        DoctorVO user = (DoctorVO) session.getAttribute("user");

        if (user == null) {
            map.put("result", "logout");
        } else if (user.getMem_num()!=doc_num) {
            map.put("result", "wrongAccess");
        } else {
            scheduleService.updateDayoffTimes(doc_num, doff_date, timesToAdd, timesToRemove);
            map.put("result", "success");
        }
        return map;
    }
}
