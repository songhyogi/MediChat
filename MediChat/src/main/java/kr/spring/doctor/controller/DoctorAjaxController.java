package kr.spring.doctor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.doctor.service.DoctorService;
import kr.spring.doctor.vo.DoctorVO;
import kr.spring.hospital.vo.HospitalVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DoctorAjaxController {
	@Autowired
	private DoctorService doctorService;
	
	//아아디 중복확인
	@GetMapping("/doctor/confirmId")
	@ResponseBody
	public Map<String,String> processDoctor(@RequestParam String mem_id){
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		DoctorVO doctor = doctorService.checkId(mem_id);
		if(doctor!=null) {
			//중복O
			mapAjax.put("result", "idDuplicated");
		}else {
			//중복X
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$",mem_id)) {
				//패턴 틀림
				mapAjax.put("result", "notMatchPattern");
			}else {
				//미중복
				mapAjax.put("result", "idNotFound");
			}
		}
		return mapAjax;
	}
	//프로필 사진 업로드
	@PostMapping("/doctor/updateDocPhoto")
	@ResponseBody
	public Map<String, String> processProfile(DoctorVO doctorVO,HttpSession session){
		
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if(user == null) {
			mapAjax.put("result", "logout");
		}else {
			doctorVO.setMem_num(user.getMem_num());
			doctorService.uploadDocProfile(doctorVO);
			
			mapAjax.put("result","success");
		}
		return mapAjax;
	}
	/*===================
		병원 목록
	===================*/
	@PostMapping("/doctor/hosList")
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(required = false) Long hos_num,
	                                   @RequestParam(required = false) String keyfield,
	                                   @RequestParam(required = false) String keyword) {
	    Map<String, Object> mapJson = new HashMap<>();
	    try {
	        // 검색 키워드를 이용하여 병원 목록을 조회하는 로직을 구현
	        List<HospitalVO> hosList = doctorService.getHosListByKeyword(keyword);

	        mapJson.put("hosList", hosList);
	        mapJson.put("success", true);
	    } catch (Exception e) {
	        mapJson.put("success", false);
	        mapJson.put("message", "병원 목록 조회 중 오류가 발생하였습니다.");
	        e.printStackTrace(); // 로깅 또는 예외처리
	    }
	    return mapJson;
	}
	/*===================
	  의사회원가입 관리자 승인
	===================*/
	@PostMapping("/updateAgree")
	@ResponseBody
	public Map<String, String> updateAgree(DoctorVO doctor,HttpSession session){
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		doctorService.updateAgree(doctor);
		
		mapJson.put("result", "success");
		
		return mapJson;
	}

}
