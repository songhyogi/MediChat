package kr.spring.member.controller;

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

import kr.spring.hospital.vo.HospitalVO;
import kr.spring.member.service.DoctorService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.DoctorVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
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
	@PostMapping("/doctor/updateDocProfile")
	@ResponseBody
	public Map<String, String> processProfile(DoctorVO doctorVO,HttpSession session){
		
		Map<String, String> mapAjax = new HashMap<String, String>();

		doctorService.uploadDocProfile(doctorVO);

		mapAjax.put("result","success");

		return mapAjax;
	}
	/*===================
		병원 목록
	===================*/
	@GetMapping("/doctor/hosList")
	@ResponseBody
	public Map<String, Object> getList(int hos_num,int pageNum,int rowCount,
							String keyfield,String keyword,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("hos_num", hos_num);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//총 개수
		int count = doctorService.selectRowCount(map);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,10,10,"list");
		
		List<HospitalVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = doctorService.getHosList(map);
		}
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		mapJson.put("count", count);
		mapJson.put("list", list);
		
		return mapJson;
	}
}
